package br.com.spotsales.utils.extensions

import com.crashlytics.android.Crashlytics
import java.text.Normalizer
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale

fun String.currencyToDecimal(locale: Locale): Float =
        try {
            NumberFormat.getCurrencyInstance(locale).parse(this).toFloat()
        } catch (ex: ParseException) {
            0f
        }

fun String.currencyBRToDecimal(): Float = currencyToDecimal(Locale("pt", "BR"))

fun String.currencyBRToInt(): Int = (currencyBRToDecimal() * 100f).toInt()

fun String.unaccent(): String =
        "\\p{InCombiningDiacriticalMarks}+"
                .toRegex()
                .replace(Normalizer.normalize(this, Normalizer.Form.NFD), "")

fun String.toHexByteArray(): ByteArray {
    val len = this.length
    val data = ByteArray(len / 2)

    try {
        var i = 0
        while (i < len) {
            data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
            i += 2
        }
    } catch (e: Exception) {
        Crashlytics.logException(e)
    }

    return data
}

fun String.toUUIDString(): String =
        try {
            "${this.substring(0, 8)}-${this.substring(8, 12)}-${this.substring(12, 16)}-" +
                    "${this.substring(16, 20)}-${this.substring(20, this.length)}"
        } catch (ex: IndexOutOfBoundsException) {
            ""
        }