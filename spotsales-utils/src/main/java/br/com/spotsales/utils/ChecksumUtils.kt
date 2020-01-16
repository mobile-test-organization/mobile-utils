package br.com.spotsales.utils

object ChecksumUtils {

    private const val FLETCHER_16_SIZE = 8
    private const val FLETCHER_32_SIZE = 16
    private const val FLETCHER_16_MODULE = 255
    private const val FLETCHER_32_MODULE = 65535

    fun fletcher16(data: ByteArray): Short =
            calculateFletcherChecksum(data, FLETCHER_16_MODULE, FLETCHER_16_SIZE).toShort()

    fun fletcher32(data: ByteArray): Int =
            calculateFletcherChecksum(data, FLETCHER_32_MODULE, FLETCHER_32_SIZE)

    private fun calculateFletcherChecksum(data: ByteArray, module: Int, size: Int): Int {
        var sum1 = 0
        var sum2 = 0

        for (d in data) {
            sum1 = (sum1 + d) % module
            sum2 = (sum2 + sum1) % module
        }

        return sum2.shl(size).or(sum1)
    }
}
