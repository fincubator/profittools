package aam.dex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import aam.dex.ui.dashboard.DashboardFragment

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment.newInstance())
                .commitNow()
        }
    }

}
