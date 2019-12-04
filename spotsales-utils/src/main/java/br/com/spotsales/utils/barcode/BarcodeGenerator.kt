package br.com.spotsales.utils.barcode

import android.graphics.Bitmap
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.common.CharacterSetECI
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import java.util.*

object BarcodeGenerator {
    private const val DEFAULT_BARCODE_SIZE = 1024

    @Throws(WriterException::class)
    fun generateBarcode(barcode: Barcode, width: Int, height: Int): Bitmap {
        val hints = EnumMap<EncodeHintType, Any>(EncodeHintType::class.java)
        hints[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.H
        hints[EncodeHintType.CHARACTER_SET] = CharacterSetECI.UTF8
        hints[EncodeHintType.MARGIN] = 0

        val safetyWidth = if (width == 0) DEFAULT_BARCODE_SIZE else width
        val safetyHeight = if (height == 0) DEFAULT_BARCODE_SIZE else height

        val formatWriter = MultiFormatWriter()
        val bitMatrix = formatWriter.encode(
            barcode.contents,
            barcode.format,
            safetyWidth,
            safetyHeight,
            hints
        )

        return createBitmap(bitMatrix, barcode.contentColor, barcode.backgroundColor)
    }

    private fun createBitmap(matrix: BitMatrix, contentColor: Int, backgroundColor: Int): Bitmap {
        val width = matrix.width
        val height = matrix.height
        val pixels = IntArray(width * height)

        for (y in 0 until height) {
            val offset = y * width
            for (x in 0 until width) {
                pixels[offset + x] = if (matrix.get(x, y)) contentColor else backgroundColor
            }
        }

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)

        return bitmap
    }
}