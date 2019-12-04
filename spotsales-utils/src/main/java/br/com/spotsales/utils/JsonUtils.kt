package br.com.spotsales.utils

import com.google.gson.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object JsonUtils {
    private const val DEFAULT_DATE_PATTERN = "MMM dd, yyyy HH:mm:ss"
    private val availableDateFormats = arrayOf(DEFAULT_DATE_PATTERN, "MMM dd, yyyy hh:mm:ss a")

    private val dateSerializer = JsonSerializer<Date> { src, _, _ ->
        if (src != null) {
            JsonPrimitive(SimpleDateFormat(DEFAULT_DATE_PATTERN, Locale.US).format(src))
        } else {
            JsonNull.INSTANCE
        }
    }

    private val dateDeserializer = JsonDeserializer { json, _, _ ->
        var date: Date? = null

        for (format in availableDateFormats) {
            try {
                date = SimpleDateFormat(format, Locale.US).parse(json.asString)
                break
            } catch (e: ParseException) {
            }
        }

        date ?: throw JsonParseException("Unparseable date: ${json.asString}")
    }

    fun defaultFormatter(): Gson =
        GsonBuilder()
            .registerTypeAdapter(Date::class.java, dateSerializer)
            .registerTypeAdapter(Date::class.java, dateDeserializer)
            .setPrettyPrinting()
            .create()
}
