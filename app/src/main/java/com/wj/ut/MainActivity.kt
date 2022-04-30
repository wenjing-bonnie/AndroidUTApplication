package com.wj.ut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var textInfo: TextView
    private lateinit var etUserName: EditText
    private lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInfo = findViewById(R.id.tv_response)
        etUserName = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        viewModel = LoginViewModelFactory().create(LoginViewModel::class.java)
        viewModel.getViewState.observe(this) { viewState ->
            println("view state is $viewState")
            textInfo.text = "The login is ${viewState::class.java.simpleName}"
        }
    }

    fun btnFirst(view: View) {
        viewModel.login(etUserName.editableText, etPassword.editableText)
    }
}