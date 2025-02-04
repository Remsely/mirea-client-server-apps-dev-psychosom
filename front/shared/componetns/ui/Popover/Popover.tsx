"use client"

import * as PopoverPrimitive from "@radix-ui/react-popover"
import { cn } from "@/shared/lib/utils"
import {ComponentPropsWithoutRef, ElementRef, forwardRef} from "react";
import styles from "./Popover.module.scss"

export const Popover = PopoverPrimitive.Root
export const PopoverTrigger = PopoverPrimitive.Trigger

export const PopoverContent = forwardRef<
    ElementRef<typeof PopoverPrimitive.Content>,
    ComponentPropsWithoutRef<typeof PopoverPrimitive.Content>
>(({ className, align = "center", sideOffset = 4, ...props }, ref) => (
    <PopoverPrimitive.Portal>
        <PopoverPrimitive.Content
            ref={ref}
            align={align}
            sideOffset={sideOffset}
            className={cn(
                styles.popoverUi,
                className
            )}
            {...props}
        />
    </PopoverPrimitive.Portal>
))
PopoverContent.displayName = PopoverPrimitive.Content.displayName