package aam.dex.screen.login

import aam.dex.R
import aam.dex.filepicker.FilePickerActivity
import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.login_fragment.*


class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: InitialViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(InitialViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        findFile.setOnClickListener { startActivityForResult(Intent(activity, FilePickerActivity::class.java), FilePickerActivity.PATH_REQUEST_CODE) }

        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                when(s.hashCode()) {
                    loginEditText.text.hashCode() -> {
                        if(s.isEmpty()) {
                            loginErrorTextView.text = "Login can not be empty!"
                            loginErrorTextView.animate().alpha(1f)
                        } else {
                            loginErrorTextView.animate().alpha(0f)
                        }
                    }
                    passwordEditText.text.hashCode() -> {
                        if(s.isEmpty()) {
                            passwordErrorTextView.text = "Password can not be empty!"
                            passwordErrorTextView.animate().alpha(1f)
                        } else {
                            passwordErrorTextView.animate().alpha(0f)
                        }
                    }
                }

                viewModel.updateData(loginEditText.text.toString(), passwordEditText.text.toString(), viewModel::validateLoginData)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        }

        loginEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == FilePickerActivity.PATH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                loginEditText.setText(it.getStringExtra(FilePickerActivity.PATH_REQUEST))
                loginEditText.setSelection(loginEditText.text.length)
            }
        }
    }

}
