'use client';

import {SessionProvider} from 'next-auth/react';
import {FC, PropsWithChildren, useState} from "react";
import {Toaster} from "react-hot-toast";
import {QueryClient, QueryClientProvider} from "@tanstack/react-query";

export const Providers: FC<PropsWithChildren> = ({children}) => {
    const [queryClient] = useState(
        () =>
            new QueryClient({
                defaultOptions: {
                    queries: {
                        staleTime: 60 * 1000, // 1 минута
                        refetchOnWindowFocus: false
                    }
                }
            })
    )

    return (
        <>
            <QueryClientProvider client={queryClient}>
                <SessionProvider>{children}</SessionProvider>
                <Toaster/>
            </QueryClientProvider>
        </>
    );
};