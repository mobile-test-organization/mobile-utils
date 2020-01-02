package br.com.spotsales.utils

import android.annotation.SuppressLint
import android.util.Log
import com.crashlytics.android.Crashlytics
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
object DateUtils {
    fun getDate(startDate: String?): Date {
        if (!startDate.isNullOrEmpty()) {
            try {
                val outFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                val dateFormatted = outFormat.format(parseISO8601Date(startDate.orEmpty()))
                return Date(dateFormatted)
            } catch (e: ParseException) {
                Crashlytics.logException(e)
            }
        }

        return Date()
    }

    fun getDateTimeZone(date: Date): String {
        val outFormat = SimpleDateFormat("Z")
        return outFormat.format(date)
    }

    fun getDateString(date: Date): String {
        val outFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return outFormat.format(date)
    }

    fun getDateTimePtBR(date: Date): String {
        val outFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        return outFormat.format(date)
    }

    fun getDatePtBR(date: Date): String {
        val outFormat = SimpleDateFormat("dd/MM/yyyy")
        return outFormat.format(date)
    }

    fun getTimePtBR(date: Date): String {
        val outFormat = SimpleDateFormat("HH:mm")
        return outFormat.format(date)
    }

    @Throws(java.text.ParseException::class)
    private fun parseISO8601Date(input: String): Date {
        val tmpInput = input.replace("T", " ").substring(0, input.indexOf('.')) + ("GMT-00" + ":00")
        val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        df.timeZone = TimeZone.getTimeZone("UTC")

        return df.parse(tmpInput)
    }

    fun getDateString(startDate: String): String {
        if (startDate.isNotEmpty()) {
            try {
                val outFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                val dateFormatted = outFormat.format(parseISO8601Date(startDate))
                return getDatePtBR(Date(dateFormatted))
            } catch (e: ParseException) {
                Crashlytics.logException(e)
            }
        }

        return getDatePtBR(Date())
    }

    fun dateNowFormattingDayOfWeek(): String {
        val dateFormatter = SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm", Locale.getDefault())
        var date = dateFormatter.format(Date())
        date = date.toLowerCase().replace("-feira", "")
        return when {
            date.toLowerCase().contains("segunda") ->
                date.toLowerCase().replace("segunda", "segunda-feira")
            date.toLowerCase().contains("terça") ->
                date.toLowerCase().replace("terça", "terça-feira")
            date.toLowerCase().contains("quarta") ->
                date.toLowerCase().replace("quarta", "quarta-feira")
            date.toLowerCase().contains("quinta") ->
                date.toLowerCase().replace("quinta", "quinta-feira")
            date.toLowerCase().contains("sexta") ->
                date.toLowerCase().replace("sexta", "sexta-feira")
            else -> date.toLowerCase()
        }
    }

}
