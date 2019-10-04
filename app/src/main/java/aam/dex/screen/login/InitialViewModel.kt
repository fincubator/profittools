package aam.dex.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InitialViewModel : ViewModel() {

    private lateinit var inputLiveData: MutableLiveData<InputData>

    fun getInputData(): LiveData<InputData> {
        if(!::inputLiveData.isInitialized) {
            inputLiveData = MutableLiveData()
            inputLiveData.value = InputData(
                login = "",
                password = "",
                valid = false
            )
        }
        return inputLiveData
    }

    fun setData(inputData: InputData) {
        inputLiveData.value = inputData
    }


    fun updateData(loginStr: String, passwordStr: String, validator: (String, String) -> Boolean) {
        inputLiveData.value = inputLiveData.value?.apply {
            login = loginStr
            password = passwordStr
            valid = validator(loginStr, passwordStr)
        }
    }

    fun validateLoginData(login: String, password: String): Boolean {
        return login.isNotEmpty() && password.isNotEmpty()
    }

    fun validateCreateAccountData(login: String, password: String): Boolean {
        return login.isNotEmpty() && password.isNotEmpty()
    }

}