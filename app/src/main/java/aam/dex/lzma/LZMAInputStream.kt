package aam.dex.lzma

import okio.buffer
import okio.source
import org.tukaani.xz.ArrayCache
import org.tukaani.xz.CorruptedInputException
import org.tukaani.xz.lz.LZDecoder
import org.tukaani.xz.lzma.LZMADecoder
import org.tukaani.xz.rangecoder.RangeDecoderFromStream
import java.io.IOException
import java.io.InputStream

const val DICT_SIZE_MAX = Integer.MAX_VALUE and 15.inv()

class LZMAInputStream(
    inputStream: InputStream
) : InputStream() {

    private val lz: LZDecoder
    private val rc: RangeDecoderFromStream
    private val lzma: LZMADecoder

    private var endReached = false
    private var remainingSize = 0L

    private val buf = ByteArray(1)

    init {

        val source = inputStream.source().buffer()

        val propByte = source.readByte()
        require(propByte <= (4 * 5 + 4) * 9 + 8) { "Invalid LZMA properties byte" }

        val dictSize = source.readIntLe()
        require(dictSize in 0..DICT_SIZE_MAX) { "LZMA dictionary is too big for this implementation" }

        val uncompSize = source.readLongLe()
        require(uncompSize >= -1) { "Uncompressed size is too big" }


        val pb = propByte / (9 * 5)
        val proppb = propByte.minus(pb * 9 * 5).toByte()
        val lp = proppb / 9
        val lc = proppb - lp * 9

        require(lc in 0..8 && lp in 0..4 && pb in 0..4) { "Invalid LZMA properties byte" }


        lz = LZDecoder(dictSize, null, ArrayCache.getDefaultCache())
        rc = RangeDecoderFromStream(inputStream)
        lzma = LZMADecoder(lz, rc, lc, lp, pb)

        remainingSize = uncompSize
    }


    override fun read(): Int {

        if (endReached) return -1

        try {
            var size = 0

                lz.setLimit(1)

                try {
                    lzma.decode()
                } catch (e: CorruptedInputException) {
                    if (remainingSize != -1L || !lzma.endMarkerDetected()) throw e
                    endReached = true
                    rc.normalize()
                }

                val copiedSize = lz.flush(buf, 1)
                size += copiedSize

                if (remainingSize >= 0) {
                    remainingSize -= copiedSize.toLong()
                    if (remainingSize == 0L) endReached = true
                }

                if (endReached) {
                    // Checking these helps a lot when catching corrupt
                    // or truncated .lzma files. LZMA Utils doesn't do
                    // the first check and thus it accepts many invalid
                    // files that this implementation and XZ Utils don't.
                    //if (!rc.isFinished || lz.hasPending()) throw CorruptedInputException()

                    //putArraysToCache()
                    return if (size == 0) -1 else buf[0].int()
                }


            return buf[0].int()

        } catch (e: IOException) {
            //exception = e
            throw e
        }

    }

    private fun Byte.int() = toInt() and 0xFF


}