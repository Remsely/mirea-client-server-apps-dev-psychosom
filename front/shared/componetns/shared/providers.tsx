'use client';

import {SessionProvider} from 'next-auth/react';
import {createContext, FC, PropsWithChildren, ReactNode, useState} from "react";
import {Toaster} from "react-hot-toast";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";
import {AuthModalForm} from "@/shared/componetns";

interface AuthModalContextProps {
    isOpenAuthModal: boolean;
    openAuthModal: () => void;
    closeAuthModal: () => void;
    setIsOpenAuthModal: (open: boolean) => void;
}

export const AuthModalContext = createContext<AuthModalContextProps | undefined>(undefined);

export function AuthProvider({ children }: { children: ReactNode }) {
    const [isOpenAuthModal, setIsOpenAuthModal] = useState(false);

    const openAuthModal = () => setIsOpenAuthModal(true);
    const closeAuthModal = () => setIsOpenAuthModal(false);

    return (
        <AuthModalContext.Provider value={{ isOpenAuthModal, openAuthModal, closeAuthModal, setIsOpenAuthModal }}>
            <AuthModalForm isOpen={isOpenAuthModal} onClose={closeAuthModal} />
            {children}
        </AuthModalContext.Provider>
    );
}

export const Providers: FC<PropsWithChildren> = ({children}) => {
    const [queryClient] = useState(
        () =>
            new QueryClient({
                defaultOptions: {
                    queries: {
                        staleTime: 60 * 1000,
                        refetchOnWindowFocus: false
                    }
                }
            })
    )

    return (
        <>
            <QueryClientProvider client={queryClient}>
                <SessionProvider>
                    <AuthProvider>
                    {children}
                    </AuthProvider>
                </SessionProvider>
                <Toaster/>
            </QueryClientProvider>
        </>
    );
};