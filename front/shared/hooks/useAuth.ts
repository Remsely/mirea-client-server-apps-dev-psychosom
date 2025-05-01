import { useContext } from "react";
import {AuthModalContext} from "@/shared/componetns";

export const useAuth = () => {
    const ctx = useContext(AuthModalContext);
    if (!ctx) throw new Error("useAuthModal must be used within AuthProvider");
    return ctx;
};