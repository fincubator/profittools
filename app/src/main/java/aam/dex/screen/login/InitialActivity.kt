package aam.dex.screen.login

import aam.dex.BaseActivity
import aam.dex.R
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_initial.*


class InitialActivity : AppCompatActivity() {

    private lateinit var viewModel: InitialViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        //------------------------------------------------------------------------------------------------

        //------------------------------------------------------------------------------------------------


        //Toast.makeText(this, cipher.algorithm, Toast.LENGTH_SHORT).show()

        //val rpcCall = RpcCall(0, "list_assets", emptyList())



        viewModel = ViewModelProviders.of(this).get(InitialViewModel::class.java)

        viewPager.adapter = InitialViewPagerAdapter(supportFragmentManager)

        signInTextView.setOnClickListener { viewPager.setCurrentItem(0, true) }
        registrationTextView.setOnClickListener { viewPager.setCurrentItem(1, true) }

        performButton.setOnClickListener {
            startActivity(Intent(this, BaseActivity::class.java))
        }

        val colorAccent = ContextCompat.getColor(this, R.color.colorAccent)
        val colorAccentInactive = ContextCompat.getColor(this, R.color.colorAccentInactive)

        viewModel.getInputData().observe(this, Observer<InputData> {
            performButton.setBackgroundColor(if(it.valid) colorAccent else colorAccentInactive)
        })

        signInTextView.post {
            val grayColor = Color.parseColor("#9e9e9e")
            val w = signInTextView.width

            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    val offset = if(position == 0) positionOffset else positionOffset+1
                    line.translationX = w * offset
                }

                override fun onPageSelected(position: Int) {
                    if(position == 0) {
                        signInTextView.setTextColor(colorAccent)
                        registrationTextView.setTextColor(grayColor)
                        performButton.text = "Sign In"
                    } else {
                        signInTextView.setTextColor(grayColor)
                        registrationTextView.setTextColor(colorAccent)
                        performButton.text = "Register"
                    }
                }
            })
        }







        /*val scarletInstance = Scarlet.Builder()
    .webSocketFactory(okHttpClient.newWebSocketFactory("wss://ws-feed.gdax.com"))
    .addMessageAdapterFactory(MoshiMessageAdapter.Factory())
    .addStreamAdapterFactory(RxJava2StreamAdapter.Factory())
    .build()

service.sendText("{\"id\":1,\"method\":\"call\",\"params\":[1,\"login\",[\"\",\"\"]]}")
service.sendText("{\"id\":2,\"method\":\"call\",\"params\":[1,\"database\",[]]}")
service.sendText("{\"id\":3,\"method\":\"call\",\"params\":[1,\"history\",[]]}")
service.sendText("{\"id\":4,\"method\":\"call\",\"params\":[1,\"network_broadcast\",[]]}")
service.sendText("{\"id\":5,\"method\":\"call\",\"params\":[2,\"get_chain_id\",[]]}")*/




    }
}
