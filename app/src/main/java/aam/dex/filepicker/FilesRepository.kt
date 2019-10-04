package aam.dex.filepicker

import android.os.AsyncTask
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import aam.dex.filepicker.model.DirectoryInfo
import aam.dex.filepicker.model.FileInfo
import java.io.File
import java.util.*

class FilesRepository {

    private val cache : LinkedList<DirectoryInfo> = LinkedList()
    private lateinit var currentDirectoryLiveData: MutableLiveData<DirectoryInfo>

    fun back(): Boolean {
        if(cache.size > 1) {
            cache.removeLast()
            currentDirectoryLiveData.value = cache.last
            return true
        }
        return false
    }

    fun next(path: String) {
        AsyncTask.execute {
            val files = File(path).listFiles().extractInfo()

            files.add(0,
                FileInfo(
                    name = "..",
                    path = "..",
                    info = "Home",
                    color = FileInfo.COLOR_GRAY,
                    type = FileInfo.HOME
                )
            )

            val directory = DirectoryInfo(path, files)

            currentDirectoryLiveData.postValue(directory)
            cache.add(directory)
        }
    }

    fun init() : MutableLiveData<DirectoryInfo> {
        currentDirectoryLiveData = MutableLiveData()
        AsyncTask.execute {
            val sdcard = Environment.getExternalStorageDirectory()
            val directory = DirectoryInfo(sdcard.path, sdcard.listFiles().extractInfo())
            currentDirectoryLiveData.postValue(directory)
            cache.add(directory)
        }
        return currentDirectoryLiveData
    }
}