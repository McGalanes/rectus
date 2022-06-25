package fr.mcgalanes.rectus.core.common.formatter

import java.text.NumberFormat
import java.util.Locale

fun Double.toPriceString(showPlusSymbol: Boolean = false): String {
    val formattedPrice = NumberFormat.getCurrencyInstance(Locale.FRANCE).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }.format(this)

    if (this > 0 && showPlusSymbol) {
        return "+${formattedPrice}"
    }

    return formattedPrice
}