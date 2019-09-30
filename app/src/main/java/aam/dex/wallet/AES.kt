package aam.dex.wallet

import org.spongycastle.crypto.BufferedBlockCipher
import org.spongycastle.crypto.engines.AESEngine
import org.spongycastle.crypto.modes.CBCBlockCipher
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.spongycastle.crypto.params.KeyParameter
import org.spongycastle.crypto.params.ParametersWithIV

class AES {

    private val aescbcpkcs7 = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()))

    fun encrypt(input: ByteArray, key: ByteArray): ByteArray {
        val keyHash = key.sha512()
        val sks = keyHash.fastCopyOfRange(0, 32)
        val iv = keyHash.fastCopyOfRange(32, 48)

        aescbcpkcs7.init(true, ParametersWithIV(KeyParameter(sks), iv))

        val output = ByteArray(aescbcpkcs7.getOutputSize(input.size))
        val offset = aescbcpkcs7.processBytes(input, 0, input.size, output, 0)
        aescbcpkcs7.doFinal(output, offset)

        return output
    }

    fun decrypt(input: ByteArray, key: ByteArray): ByteArray {
        val keyHash = key.sha512()
        val sks = keyHash.fastCopyOfRange(0, 32)
        val iv = keyHash.fastCopyOfRange(32, 48)

        aescbcpkcs7.init(false, ParametersWithIV(KeyParameter(sks), iv))

        val output = ByteArray(aescbcpkcs7.getOutputSize(input.size))
        val offset = aescbcpkcs7.processBytes(input, 0, input.size, output, 0)
        aescbcpkcs7.doFinal(output, offset)

        return output
    }

}