package fr.mcgalanes.rectus.core.common.formatter

import androidx.compose.ui.text.intl.Locale as AndroidxLocale
import androidx.compose.ui.text.capitalize
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale

fun LocalDateTime.toShortDateString(locale: AndroidxLocale = AndroidxLocale.current): String {
    val formattedMonth =
        month.getDisplayName(
            TextStyle.FULL,
            Locale(locale.language)
        ).lowercase().capitalize(locale)

    return "$dayOfMonth $formattedMonth"
}

fun LocalDateTime.toFullDateWithTimeString(
    locale: AndroidxLocale = AndroidxLocale.current
): String {
    val loc = Locale(locale.language)

    val formattedDayOfWeek = dayOfWeek
        .getDisplayName(TextStyle.FULL, loc)
        .lowercase()
        .capitalize(locale)

    val formattedMonth = month
        .getDisplayName(TextStyle.FULL, loc)
        .lowercase()
        .capitalize(locale)

    val formattedMinute = minute.run { if (this < 10) "0$minute" else "$minute" }

    return "$formattedDayOfWeek $dayOfMonth $formattedMonth $year • $hour:$formattedMinute"
}