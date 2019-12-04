package br.com.spotsales.utils.extensions

import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

fun Float.toCurrency(locale: Locale) = NumberFormat.getCurrencyInstance(locale).format(this)

fun Float.toBRCurrency() = toCurrency(Locale("pt", "BR")).format(this)

fun Float.toCents() = (this * 100f).roundToInt()