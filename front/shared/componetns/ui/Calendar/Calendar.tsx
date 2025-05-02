"use client"

import "./Calendar.scss"
import * as React from "react"
import { ChevronLeft, ChevronRight } from "lucide-react"
import { DayPicker } from "react-day-picker"
import "react-day-picker/style.css";
import {ru} from "date-fns/locale"
import './Calendar.scss'

export type CalendarProps = React.ComponentProps<typeof DayPicker>

export function Calendar({
                      className = "",
                      showOutsideDays = true,
                      ...props
                  }: CalendarProps) {
    return (
        <DayPicker
            animate
            showOutsideDays={showOutsideDays}
            className={`calendar ${className}`}
            locale={ru}
            components={{
                // eslint-disable-next-line @typescript-eslint/ban-ts-comment
                // @ts-expect-error
                IconLeft: ({ className = "", ...props }) => (
                    <ChevronLeft className={`icon-left ${className}`} {...props} />
                ),
                IconRight: ({ className = "", ...props }) => (
                    <ChevronRight className={`icon-right ${className}`} {...props} />
                ),
            }}
            {...props}
        />
    )
}
Calendar.displayName = "Calendar";