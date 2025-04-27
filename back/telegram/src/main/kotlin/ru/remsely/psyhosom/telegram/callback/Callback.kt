package ru.remsely.psyhosom.telegram.callback

enum class Callback(val value: String) {
    CANCEL_CONSULTATION("cancel_consultation"),
    REJECT_CONSULTATION("reject_consultation"),
    CONFIRM_CONSULTATION("confirm_consultation"),
}
