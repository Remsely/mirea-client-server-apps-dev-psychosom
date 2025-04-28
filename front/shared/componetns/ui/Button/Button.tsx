import * as React from 'react';
import { Slot } from '@radix-ui/react-slot';
import styles from './Button.module.scss'
import {Loader} from "lucide-react";
import {cn} from "@/shared/utils";

export interface ButtonProps
    extends React.ButtonHTMLAttributes<HTMLButtonElement> {
    asChild?: boolean;
    loading?: boolean;
}

export const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
    ({ className, asChild = false, disabled, loading, children,  ...props }, ref) => {
        const Comp = asChild ? Slot : 'button';

        return (
            <Comp
                className={cn(
                    styles.buttonUi,
                    className
                )}
                disabled={disabled || loading}
                ref={ref}
                {...props}>
                {!loading ? children : <Loader className={styles.buttonUiLoader} />}
            </Comp>
        );
    }
);

Button.displayName = 'Button';