import {ComponentProps, forwardRef} from "react";
import styles from "./Input.module.scss"
import {cn} from "@/shared/utils";

export const Input = forwardRef<HTMLInputElement, ComponentProps<"input">>(
    ({ className, type, ...props }, ref) => {
        return (
            <input
                type={type}
                className={cn(
                    styles.inputUi,
                    className
                )}
                ref={ref}
                {...props}
            />
        )
    }
)
Input.displayName = "Input"