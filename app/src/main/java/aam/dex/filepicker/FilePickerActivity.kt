package aam.dex.filepicker

import aam.dex.R
import aam.dex.observe
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_file_picker.*
import android.content.Intent

class FilePickerActivity : AppCompatActivity() {

    private lateinit var viewModel: FilesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_picker)

        setSupportActionBar(toolbar)

        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        viewModel = ViewModelProviders.of(this).get(FilesViewModel::class.java)

        val adapter = FilePickerRecyclerAdapter(
            context = this,
            nextListener = { viewModel.next(it) },
            prevListener = { viewModel.back() },
            resultListener = {
                setResult(RESULT_OK, Intent().apply { putExtra(PATH_REQUEST,it) })
                finish()
            }
        )

        filesRecyclerView.layoutManager = LinearLayoutManager(this)
        filesRecyclerView.adapter = adapter

        val sdcard = Environment.getExternalStorageDirectory().path
        viewModel.getCurrentDirectory().observe(this) {
            title = if(it.path == sdcard) "Internal Memory" else it.path.substringAfterLast("/")
            adapter.notifyWith(it.files)
        }
    }

    override fun onBackPressed() {
        if(!viewModel.back()) super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val PATH_REQUEST = "path"
        const val PATH_REQUEST_CODE = 12
    }

}
