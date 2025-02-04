// eslint-disable-next-line @typescript-eslint/no-unused-vars
import NextAuth from "next-auth";

declare module "next-auth" {
    interface Session {
        token?: string;
        webSocketToken?: string;
        accountConfirmationUrl?: string;
        user: {
            firstname?: string | null;
            lastname?: string | null;
            username?: string | null;
        };
    }

    interface User {
        firstname?: string | null;
        lastname?: string | null;
        username?: string | null;
    }
}

declare module "next-auth/jwt" {
    interface JWT {
        token?: string;
        firstname?: string | null;
        lastname?: string | null;
        username?: string | null;
        webSocketToken?: string;
        accountConfirmationUrl?: string;
    }
}