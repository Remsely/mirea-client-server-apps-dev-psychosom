"use client";

import { FormEvent, useEffect, useState } from "react";
import { useSession } from "next-auth/react";
import { Button, Input } from "@/shared/componetns/ui";
import styles from "./ProfileForm.module.scss";
import { redirect } from "next/navigation";
import { LoadingSpinner } from "@/shared/componetns/shared";
import { cn } from "@/shared/utils";
import { useProfile, useUpdateProfile } from "@/shared/hooks/useProfile";

export function ProfileForm() {
    const { data: session, status } = useSession();
    const { data: profile, isLoading, error } = useProfile();
    const {
        mutate: updateProfile,
        isPending: isUpdating,
        error: updateError,
        isSuccess: isUpdated,
    } = useUpdateProfile();

    const [message, setMessage] = useState("");

    useEffect(() => {
        if (error) setMessage(error.message);
        else setMessage("");
    }, [error]);

    useEffect(() => {
        if (updateError) setMessage(updateError.message);
        else if (isUpdated) setMessage("Профиль успешно обновлён!");
    }, [updateError, isUpdated]);

    if (status === "loading" || isLoading) {
        return (
            <div className={cn("container", styles.block)}>
                <div className={styles.loaderWrapper}>
                    <LoadingSpinner />
                </div>
            </div>
        );
    }

    if (status === "unauthenticated") {
        redirect("/");
    }

    const handleUpdate = (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setMessage("");
        if (!session || !profile) return;

        const formData = new FormData(e.currentTarget);
        const updatedData = {
            firstName: String(formData.get("firstName") || ""),
            lastName: String(formData.get("lastName") || ""),
        };

        updateProfile(updatedData);
    };

    return (
        <div className={cn("container", styles.block)}>
            <div>
                <form onSubmit={handleUpdate}>
                    <h1 className={styles.title}>Профиль пользователя</h1>
                    {message && <p className={styles.message}>{message}</p>}
                    {profile && (
                        <>
                            <div>
                                <label>
                                    Имя:
                                    <Input
                                        name="firstName"
                                        defaultValue={profile.firstName}
                                        required
                                        disabled={isUpdating}
                                    />
                                </label>
                            </div>
                            <div>
                                <label>
                                    Фамилия:
                                    <Input
                                        name="lastName"
                                        defaultValue={profile.lastName}
                                        required
                                        disabled={isUpdating}
                                    />
                                </label>
                            </div>
                            <Button className={styles.button} type="submit" disabled={isUpdating}>
                               Обновить профиль
                            </Button>
                        </>
                    )}
                </form>
            </div>
        </div>
    );
}