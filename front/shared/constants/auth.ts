import {AuthOptions} from "next-auth";
import CredentialsProvider from "next-auth/providers/credentials";
import { cookies } from "next/headers";

interface AuthRequest {
    username: string;
    password: string;
    firstName?: string;
    lastName?: string;
}

const apiRequest = async (url: string, body: AuthRequest) => {
    try {
        const res = await fetch(url, {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(body),
        });

        const data = await res.json();

        if (!res.ok) {
            console.error("Запрос к API не выполнен:", data);
            return {ok: false, data};
        }

        return {ok: true, data};
    } catch (error) {
        console.error("Исключение при запросе к API:", error);
        return {ok: false, data: null};
    }
};

export const authConfig: AuthOptions = {
    providers: [
        CredentialsProvider({
            name: "Credentials",
            credentials: {
                username: {label: "Username", type: "text"},
                password: {label: "Password", type: "password"},
                firstName: {label: "FirstName", type: "text"},
                lastName: {label: "LastName", type: "text"},
            },
            async authorize(credentials, req) {
                if (!credentials?.username || !credentials?.password) {
                    console.warn("Отсутствуют учетные данные");
                    return null;
                }

                const isRegister = req.body?.isRegister;

                const endpoint = isRegister
                    ? `${process.env.BACKEND_URL}/api/v1/auth/register/patient`
                    : `${process.env.BACKEND_URL}/api/v1/auth/login`;

                const requestBody: AuthRequest = {
                    username: credentials.username,
                    password: credentials.password,
                };

                if (isRegister) {
                    if (!credentials.firstName || !credentials.lastName) {
                        console.warn("Для регистрации требуются имя и фамилия");
                        return null;
                    }
                    requestBody.firstName = credentials.firstName;
                    requestBody.lastName = credentials.lastName;
                }

                const {ok, data} = await apiRequest(endpoint, requestBody);

                (await cookies()).set("backend_token", data.token, {
                    httpOnly: true,
                    secure: !!process.env.NEXTAUTH_SECRET,
                    sameSite: "lax",
                    path: "/",
                    maxAge: 60 * 60 * 24 * 7,
                });

                return ok ? data : null;
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