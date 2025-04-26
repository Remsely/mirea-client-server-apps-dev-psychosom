"use client"

import "./Calendar.scss"
import * as React from "react"
import { ChevronLeft, ChevronRight } from "lucide-react"
import { DayPicker } from "react-day-picker"

export type CalendarProps = React.ComponentProps<typeof DayPicker>

export function Calendar({
                      className = "",
                      classNames = {},
                      showOutsideDays = true,
                      ...props
                  }: CalendarProps) {
    return (
        <DayPicker
            showOutsideDays={showOutsideDays}
            className={`calendar ${className}`}
            classNames={{
                months: "months",
                month: "month",
                caption: "caption",
                caption_label: "caption-label",
                nav: "nav",
                nav_button: "nav-button",
                nav_button_previous: "nav-button-previous",
                nav_button_next: "nav-button-next",
                table: "table",
                head_row: "head-row",
                head_cell: "head-cell",
                row: "row",
                cell: "cell",
                day: "day",
                day_range_start: "day-range-start",
                day_range_end: "day-range-end",
                day_selected: "day-selected",
                day_today: "day-today",
                day_outside: "day-outside",
                day_disabled: "day-disabled",
                day_range_middle: "day-range-middle",
                day_hidden: "day-hidden",
                ...classNames,
            }}
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