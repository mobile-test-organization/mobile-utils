package br.com.spotsales.utils.extensions

import java.text.Normalizer
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

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