@file:Suppress("NOTHING_TO_INLINE")

package aam.dex.wallet

inline fun ByteArray.fastCopyOfRange(start: Int, end: Int): ByteArray {
    val arr = ByteArray(end - start)
    System.arraycopy(this, start, arr, 0, arr.size)
    return arr
}


