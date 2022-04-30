package com.wj.ut

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var textInfo: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInfo = findViewById(R.id.tv_response)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getViewState.observe(this) { viewState ->
            println("view state is $viewState")
            textInfo.text = viewState.toString()
        }


    }

    fun btnFirst(view: View) {
        viewModel.login("zhangsan", "123456")
    }

    fun btnSecond(view: View) {
        viewModel.login("zhangsan", "12345678")
    }

    fun btnThird(view: View) {
        viewModel.login("zhangsan", "123")
    }
}