'use client';

import { SessionProvider } from 'next-auth/react';
import {FC, PropsWithChildren} from "react";
import {Toaster} from "react-hot-toast";

export const Providers: FC<PropsWithChildren> = ({ children }) => {
    return (
        <>
            <SessionProvider>{children}</SessionProvider>
            <Toaster />
        </>
    );
};