package aam.dex.wallet

import java.security.SecureRandom
import java.text.SimpleDateFormat
import java.util.*

class WalletFactory {

    private val aes = AES()
    private val ec = EC()

    fun create(name: String, password: String, brainKey: String, brainKeySequence: Int, chainId: String): Wallet {

        val decryptedKey = ByteArray(32)
        SecureRandom().nextBytes(decryptedKey)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
        val created = dateFormat.format(Date())

        return Wallet(
            name = name,
            id = name,
            chainId = chainId,
            encryptionKey = aes.encrypt(decryptedKey, password.toByteArray()).encodeHex(),
            passwordPubKey = ec.btsPubFromPrivate(password.toByteArray()),
            brainKeyEncrypted = aes.encrypt(brainKey.toByteArray(), decryptedKey).encodeHex(),
            brainKeyPubKey = ec.btsPubFromPrivate(brainKey.toByteArray()),
            brainKeySequence = brainKeySequence,
            brainKeyBackupDate = null,
            created = created,
            lastModified = created,
            backupDate = null
        )

    }

}