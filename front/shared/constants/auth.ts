import { AuthOptions } from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import { cookies } from "next/headers";
import { authService } from "@/shared/services";

export const authConfig: AuthOptions = {
    providers: [
        CredentialsProvider({
            name: "Credentials",
            credentials: {
                username: { label: "Username", type: "text" },
                password: { label: "Password", type: "password" },
                firstName: { label: "FirstName", type: "text" },
                lastName: { label: "LastName", type: "text" },
            },
            async authorize(credentials, req) {
                if (!credentials?.username || !credentials?.password) {
                    console.warn("Отсутствуют учетные данные");
                    return null;
                }

                const isRegister = req.body?.isRegister;

                try {
                    if (isRegister) {
                        if (!credentials.firstName || !credentials.lastName) {
                            console.warn("Для регистрации требуются имя и фамилия");
                            return null;
                        }

                        const response = await authService.register({
                            username: credentials.username,
                            password: credentials.password,
                            firstName: credentials.firstName,
                            lastName: credentials.lastName,
                        });

                        const { webSocketToken, accountConfirmationUrl } = response;

                        return {
                            id: credentials.username,
                            webSocketToken,
                            accountConfirmationUrl,
                        };
                    } else {
                        const response = await authService.login({
                            username: credentials.username,
                            password: credentials.password,
                        });

                        const { token } = response;

                        (await cookies()).set("backend_token", token, {
                            httpOnly: true,
                            secure: !!process.env.NEXTAUTH_SECRET,
                            sameSite: "lax",
                            path: "/",
                            maxAge: 60 * 60 * 24 * 7,
                        });

                        return {
                            id: credentials.username,
                            token,
                        };
                    }
                } catch (error) {
                    console.error("Ошибка авторизации:", error);
                    return null;
                }
            },
        }),
    ],
    secret: process.env.NEXTAUTH_SECRET,
    session: {
        strategy: "jwt",
        maxAge: 60 * 60 * 24 * 7,
    },
    callbacks: {
        async jwt({ token, user }) {
            if (user) {
                token = { ...token, ...user };
            }
            return token;
        },
        async session({ session, token }) {
            session.webSocketToken = token.webSocketToken;
            session.accountConfirmationUrl = token.accountConfirmationUrl;
            return session;
        },
    },
};