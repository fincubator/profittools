package aam.dex

import aam.dex.wallet.*
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import org.spongycastle.crypto.digests.SHA512Digest
import org.spongycastle.jce.provider.BouncyCastleProvider
import java.security.MessageDigest
import java.security.Security


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
