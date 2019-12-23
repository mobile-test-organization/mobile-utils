package br.com.spotsales.utils

import org.hashids.Hashids
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import kotlin.math.absoluteValue

object SecurityUtils {

    fun hashidsEncode(code: String, salt: String): String {
        val minValue = Byte.MIN_VALUE.toLong().absoluteValue
        val longList =
            code.toByteArray()
                .map { i ->
                    if (i < 0) {
                        minValue * 2 + i.toLong()
                    } else {
                        i.toLong()
                    }
                }
                .toLongArray()

        return Hashids(salt).encode(*longList)
    }

    fun hashidsDecode(code: String, salt: String): String {
        val result = Hashids(salt).decode(code)
        val bytes = ByteArray(result.size)

        (result.indices).forEach { bytes[it] = result[it].toByte() }

        return bytes.toString(Charsets.UTF_8)
    }

    fun bcryptEncode(password: String, salt: String): String = BCrypt.hashpw(password, salt)

    fun generateUUID(): String = UUID.randomUUID().toString()

}
