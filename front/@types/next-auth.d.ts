import NextAuth from "next-auth";

declare module "next-auth" {
    interface Session {
        user: {
            firstname?: string | null;
            lastname?: string | null;
            phone?: string | null;
            telegram?: string | null;
            jwtToken?: string;
            webSocketToken?: string;
            tbBotConfirmationUrl?: string;
        };
    }

    interface User {
        firstname?: string | null;
        lastname?: string | null;
        phone?: string | null;
        telegram?: string | null;
        tbBotConfirmationUrl?: string;
        webSocketToken?: string;
        jwtToken?: string;
    }
}

declare module "next-auth/jwt" {
    interface JWT {
        jwtToken?: string | undefined;
        firstname?: string | null;
        lastname?: string | null;
        phone?: string | null;
        telegram?: string | null;
        tbBotConfirmationUrl?: string;
        webSocketToken?: string;
    }
}