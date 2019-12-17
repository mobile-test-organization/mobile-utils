package br.com.spotsales.utils

import org.hashids.Hashids
import org.mindrot.jbcrypt.BCrypt
import java.util.*

object SecurityUtils {

    fun hashidsEncode(code: String, salt: String): String {
        val hashids = Hashids(salt)
        val longList = code.toByteArray().map { i -> i.toLong() }.toLongArray()
        return hashids.encode(*longList)
    }

    fun hashidsDecode(code: String, salt: String): String {
        val hashids = Hashids(salt)
        val result = hashids.decode(code)
        val decodedBytes = result.map { d -> d.toByte() }

        decodedBytes.forEach { a ->
            print(String(listOf(a).toByteArray()))
        }

        return String(decodedBytes.toByteArray())
    }

    fun bcryptEncode(password: String, salt: String): String = BCrypt.hashpw(password, salt)

    fun generateUUID(): String = UUID.randomUUID().toString()

}
