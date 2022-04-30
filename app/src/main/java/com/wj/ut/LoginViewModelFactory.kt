package com.wj.ut

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * created by wenjing.liu at 2022/4/30
 */
class LoginViewModelFactory() : ViewModelProvider.Factory {
    private val api: HttpApi = HttpApi()
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(api, dispatcher) as T
    }
}