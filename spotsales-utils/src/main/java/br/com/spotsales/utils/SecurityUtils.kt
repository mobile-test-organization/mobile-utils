package br.com.spotsales.utils

import android.util.Base64
import com.crashlytics.android.Crashlytics
import org.hashids.Hashids
import org.mindrot.jbcrypt.BCrypt
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
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

    fun aesEncode(data: String, security: String): String? {
        try {
            val iv = IvParameterSpec(security.toByteArray(Charsets.UTF_8))
            val key = SecretKeySpec(security.toByteArray(Charsets.UTF_8), "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, key, iv)

            val textBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))

            return Base64.encodeToString(textBytes, Base64.DEFAULT)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Crashlytics.logException(ex)
        }

        return null
    }

    fun aesDecode(data: String, security: String): String? {
        try {
            val iv = IvParameterSpec(security.toByteArray(Charsets.UTF_8))
            val key = SecretKeySpec(security.toByteArray(Charsets.UTF_8), "AES")

            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, key, iv)

            val textBytes = cipher.doFinal(Base64.decode(data, Base64.DEFAULT))

            return textBytes.toString(Charsets.UTF_8)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Crashlytics.logException(ex)
        }

        return null
    }
}
