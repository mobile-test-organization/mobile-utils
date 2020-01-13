package br.com.spotsales.utils.extensions

fun ByteArray.toHexString(): String {
    val hexString = "0123456789ABCDEF"
    val size = this.size * 2
    val sb = StringBuilder(size)

    for (i in this.indices) {
        sb.append(hexString[this[i].toInt() and 0xF0 ushr 4])
        sb.append(hexString[this[i].toInt() and 0x0F ushr 0])
    }

    return sb.toString()
}