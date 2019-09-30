package aam.dex.wallet


private val HEX_ARRAY = "0123456789abcdef".toCharArray()

fun ByteArray.encodeHex(): String {
    val hexChars = CharArray(size * 2)
    for (j in indices) {
        val v = this[j].toInt() and 0xFF
        hexChars[j * 2] = HEX_ARRAY[v.ushr(4)]
        hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
    }
    return String(hexChars)
}

fun String.decodeHex(): ByteArray {
    val data = ByteArray(length / 2)
    var i = 0
    while (i < length) {
        data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}