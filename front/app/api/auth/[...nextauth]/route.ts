import NextAuth, { AuthOptions } from "next-auth";
import GitHubProvider from "next-auth/providers/github";
import CredentialsProvider from "next-auth/providers/credentials";

const apiRequest = async (url: string, body: Record<string, unknown>) => {
    const res = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
    });
    const data = await res.json();
    return { ok: res.ok, data };
};

export const authOptions: AuthOptions = {
    providers: [
        GitHubProvider({
            clientId: process.env.GITHUB_ID || "",
            clientSecret: process.env.GITHUB_SECRET || "",
        }),
        CredentialsProvider({
            name: "Credentials",
            credentials: {
                username: { label: "Username", type: "text"},
                password: { label: "Password", type: "password" },
            },
            async authorize(credentials, req) {
                if (!credentials?.username || !credentials?.password) return null;

                const endpoint = req.body?.isRegister
                    ? "http://localhost:8080/api/v1/auth/register/patient"
                    : "http://localhost:8080/api/v1/auth/login";

                const { ok, data } = await apiRequest(endpoint, {
                    username: credentials.username,
                    password: credentials.password,
                });

                return ok ? data : null;
            },
        }),
    ],
    session: { strategy: "jwt" },
    callbacks: {
        async jwt({ token, user}) {
            if (user) {
                token = { ...token, ...user };
            }
            return token;
        },
        async session({ session, token }) {
            session.user = {
                phone: token.phone,
                telegram: token.telegram,
                webSocketToken: token.webSocketToken,
                tbBotConfirmationUrl: token.tbBotConfirmationUrl,
            };
            return session;
        },
    },
};

const handler = NextAuth(authOptions);

export { handler as GET, handler as POST };