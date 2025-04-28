import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import {FetchError} from "@/shared/utils";
import {toast} from "react-hot-toast";
import {ProfileData, profileService} from "../services";

export function useProfile() {
    return useQuery<ProfileData, Error>({
        queryKey: ["profile"],
        queryFn: () => profileService.findProfile(),
        retry: (failureCount, error) => {
            if (error.message === "Требуется авторизация") return false;
            return failureCount < 2;
        },
    });
}

export function useUpdateProfile() {
    const queryClient = useQueryClient();

    return useMutation<ProfileData, Error, ProfileData>({
        mutationFn: (data) => profileService.updateProfile(data),
        onSuccess: (data) => {
            queryClient.setQueryData(["profile"], data);
        },
        onError: (error) => {
            const statusErrorMessages: Record<number, string> = {
                400: "Некорректные данные профиля. Проверьте правильность заполнения формы.",
                401: "Сессия истекла или требуется авторизация. Пожалуйста, войдите в аккаунт.",
                500: "Внутренняя ошибка сервера. Попробуйте позже.",
            };

            if (error instanceof FetchError) {
                if (statusErrorMessages[error.statusCode]) {
                    toast.error(statusErrorMessages[error.statusCode]);
                    return;
                }
            }

            toast.error(error.message || "Не удалось обновить профиль. Попробуйте позже.");
        }
    });
}