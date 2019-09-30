package aam.dex.wallet

import org.spongycastle.crypto.params.ECDomainParameters
import org.spongycastle.crypto.ec.CustomNamedCurves
import org.spongycastle.math.ec.FixedPointCombMultiplier
import java.math.BigInteger
import org.spongycastle.math.ec.FixedPointUtil

class EC {

    val CURVE_PARAMS = CustomNamedCurves.getByName("secp256k1")
    val CURVE: ECDomainParameters
    val HALF_CURVE_ORDER: BigInteger

    val multiplier = FixedPointCombMultiplier()

    init {

        FixedPointUtil.precompute(CURVE_PARAMS.g, 12)
        CURVE = ECDomainParameters(CURVE_PARAMS.curve, CURVE_PARAMS.g, CURVE_PARAMS.n, CURVE_PARAMS.h)
        HALF_CURVE_ORDER = CURVE_PARAMS.n.shiftRight(1)

    }

    fun btsPubFromPrivatenh(password: ByteArray): String {
        val point = multiplier.multiply(CURVE.g, BigInteger(1, password))
        val pub = point.getEncoded(true)

        val checksum = pub.ripemd160().fastCopyOfRange(0, 4)

        return "BTS" + (pub + checksum).encodeBase58()
    }

    fun btsPubFromPrivate(password: ByteArray): String {
        val point = multiplier.multiply(CURVE.g, BigInteger(1, password.sha256()))
        val pub = point.getEncoded(true)

        val checksum = pub.ripemd160().fastCopyOfRange(0, 4)

        return "BTS" + (pub + checksum).encodeBase58()
    }

}

