package aam.dex.filepicker

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import aam.dex.filepicker.model.DirectoryInfo

class FilesViewModel : ViewModel() {

    private val filesRepository = FilesRepository()

    private lateinit var currentDirectoryLiveData: MutableLiveData<DirectoryInfo>

    fun next(path: String) {
        filesRepository.next(path)
    }

    fun back(): Boolean {
        return filesRepository.back()
    }

    fun getCurrentDirectory(): MutableLiveData<DirectoryInfo> {
        if(!::currentDirectoryLiveData.isInitialized) {
            currentDirectoryLiveData = filesRepository.init()
        }
        return currentDirectoryLiveData
    }

}