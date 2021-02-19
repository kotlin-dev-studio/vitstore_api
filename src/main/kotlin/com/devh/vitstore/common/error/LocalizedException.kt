package com.devh.vitstore.common.error

import java.util.*
import java.util.Locale

object Messages {
    fun getMessageForLocale(messageKey: String): String {
        return ResourceBundle.getBundle("messages", Locale.JAPANESE).getString(messageKey)
    }
}

class LocalizedException(
    private val exceptionKey: String,
    private val keyCode: String = "default"
) {
    fun getLocalizedMessage(): String {
        return Messages.getMessageForLocale("api_error.$exceptionKey.$keyCode.message")
    }

    fun getLocalizedCode(): Int {
        return Messages.getMessageForLocale("api_error.$exceptionKey.$keyCode.code").toIntOrNull() ?: 0
    }
}

fun String.toCamelCase() =
    split('_').joinToString("", transform = String::capitalize)

fun String.toSnakeCase() = replace(humps, "_").toLowerCase()
private val humps = "(?<=.)(?=\\p{Upper})".toRegex()
