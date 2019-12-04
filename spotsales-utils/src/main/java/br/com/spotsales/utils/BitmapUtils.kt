package br.com.spotsales.spotsalesticket.utils

import android.graphics.Bitmap
import android.os.Environment
import android.util.Base64
import com.crashlytics.android.Crashlytics
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtils {
    fun bitmapToPngBase64(bitmap: Bitmap, quality: Int): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return "data:image/png;base64,${Base64.encodeToString(byteArray, Base64.DEFAULT)}"
    }

    fun saveJpegBitmap(bmp: Bitmap, parent: String, fileName: String, quality: Int): String? {
        val dest = File(Environment.getExternalStorageDirectory().absolutePath, parent)
        val filePath = dest.absolutePath + File.separator + fileName
        val file = File(filePath)

        if (!dest.exists()) {
            dest.mkdirs()
        }

        return try {
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, out)
            out.flush()
            out.close()
            filePath
        } catch (ex: IOException) {
            Crashlytics.logException(ex)
            null
        }
    }
}