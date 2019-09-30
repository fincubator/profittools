package aam.dex.wallet

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wallet(
    @Json(name = "public_name")             val name: String,
    @Json(name = "id")                      val id: String,
    @Json(name = "chain_id")                val chainId: String,
    @Json(name = "encryption_key")          val encryptionKey: String,
    @Json(name = "password_pubkey")         val passwordPubKey: String,
    @Json(name = "encrypted_brainkey")      val brainKeyEncrypted: String,
    @Json(name = "brainkey_pubkey")         val brainKeyPubKey: String,
    @Json(name = "brainkey_sequence")         val brainKeySequence: Int,
    @Json(name = "brainkey_backup_date")    val brainKeyBackupDate: String?,
    @Json(name = "created")                 val created: String,
    @Json(name = "last_modified")           val lastModified: String,
    @Json(name = "backup_date")             val backupDate: String?
)