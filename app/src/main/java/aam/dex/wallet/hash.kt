package aam.dex.wallet

import java.security.MessageDigest

fun ByteArray.sha256() = MessageDigest.getInstance("SHA256", "SC").digest(this)
fun ByteArray.sha512() = MessageDigest.getInstance("SHA512", "SC").digest(this)
fun ByteArray.ripemd160() = MessageDigest.getInstance("RIPEMD160", "SC").digest(this)