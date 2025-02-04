"use client";

import {FormEvent, useEffect, useState} from "react";
import {useSession} from "next-auth/react";
import {Button, Input} from "@/shared/componetns/ui";
import styles from "./ProfileForm.module.scss";
import {cn} from "@/shared/lib/utils";
import {redirect} from "next/navigation";
import {LoadingSpinner} from "@/shared/componetns/shared";

interface ProfileData {
    firstName: string;
    lastName: string;
}

export function ProfileForm() {
    const {data: session, status} = useSession();
    const [profile, setProfile] = useState<ProfileData | null>(null);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");

    useEffect(() => {
        if (status === "authenticated") {
            loadProfile();
        }
    }, [status]);

    const loadProfile = async () => {
        setLoading(true);
        setMessage("");

        try {
            const res = await fetch(`/api/proxy/api/v1/patients`);

            if (res.status === 401) {
                setMessage("Требуется авторизация");
                return;
            }

            if (res.ok) {
                const data = await res.json();
                setProfile(data);
            } else {
                setMessage("Не удалось загрузить профиль");
            }
        } catch (error) {
            console.error("Ошибка загрузки профиля:", error);
            setMessage("Ошибка соединения");
        } finally {
            setLoading(false);
        }
    };

    const handleUpdate = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!session) return;

        setLoading(true);
        setMessage("");

        const formData = new FormData(e.currentTarget);
        const updatedData = {
            firstName: formData.get("firstName"),
            lastName: formData.get("lastName"),
        };

        try {
            const res = await fetch(`/api/proxy/api/v1/patients`, {
                method: "PUT",
                body: JSON.stringify(updatedData),
            });

            if (res.status === 401) {
                setMessage("Сессия истекла");
                return;
            }

            if (res.ok) {
                const data = await res.json();
                setProfile(data);
                setMessage("Профиль успешно обновлён!");
            } else {
                const error = await res.json();
                setMessage(error.message || "Ошибка обновления профиля");
            }
        } catch (error) {
            console.error("Ошибка обновления:", error);
            setMessage("Ошибка соединения");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className={cn("container", styles.block)}>
            <div>
                <form onSubmit={handleUpdate}>
                    <h1 className={styles.title}>Профиль пользователя</h1>
                    {status === "loading" ? (
                        <div className={styles.loaderWrapper}>
                            <LoadingSpinner/>
                        </div>
                    ) : status === "unauthenticated" ? (
                        redirect('/')
                    ) : message ? (
                        <p className={styles.message}>{message}</p>
                    ) : profile ? (
                        <>
                            <div>
                                <label>
                                    Имя:
                                    <Input
                                        name="firstName"
                                        defaultValue={profile.firstName}
                                        required
                                        disabled={loading}
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
                                        disabled={loading}
                                    />
                                </label>
                            </div>
                            <Button className={styles.button} type="submit" disabled={loading}>
                                {loading ? "Обновление..." : "Обновить профиль"}
                            </Button>
                        </>
                    ) : (
                        <div className={styles.loaderWrapper}>
                            <LoadingSpinner/>
                        </div>
                    )}
                </form>
            </div>
        </div>
    );
}