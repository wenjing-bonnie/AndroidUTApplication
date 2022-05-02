package com.wj.ut

import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

/**
 * create by wenjing.liu at 2022/4/30
 */
class LoginViewModel(private val api: HttpApi, private val dispatcher: CoroutineDispatcher) :
    ViewModel() {
    private var tag = "123"
    private var viewState = MutableLiveData<ViewState>()
    val getViewState: LiveData<ViewState>
        get() = viewState

    fun login(userName: Editable?, password: Editable?) {
        when (userName.toString()) {
            "vip" -> {
                viewState.value = ViewState.VipUser
                return
            }
        }
        viewModelScope.launch(dispatcher) {
            val response = api.login(userName.toString(), password.toString())
            println("The receiver the response is $response")
            when (response) {
                0 -> viewState.postValue(ViewState.LoginSuccess)
                1 -> viewState.postValue(ViewState.LoginFailure)
                401 -> viewState.postValue(ViewState.LoginError401)
                else -> viewState.postValue(ViewState.InvalidPassword("password 123456 -> 0, 12345678 -> 1, 123 -> 401"))
            }
        }
    }

    sealed class ViewState {
        object LoginSuccess : ViewState()
        object LoginFailure : ViewState()
        object LoginError401 : ViewState()
        data class InvalidPassword(val error: String) : ViewState()
        object VipUser : ViewState()
    }

}