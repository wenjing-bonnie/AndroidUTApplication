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
class MainViewModel(private val api: HttpApi, private val dispatcher: CoroutineDispatcher) :
    ViewModel() {

    private var viewState = MutableLiveData<ViewState>()
    val getViewState: LiveData<ViewState>
        get() = viewState

    fun login(userName: String, password: String) {
        viewModelScope.launch(dispatcher) {
            when (api.login(userName.toString(), password.toString())) {
                0 -> viewState.value = ViewState.LoginSuccess
                1 -> viewState.value = ViewState.LoginFailure
                401 -> viewState.value = ViewState.LoginError401
            }
        }

    }

    sealed class ViewState {
        object LoginSuccess : ViewState()
        object LoginFailure : ViewState()
        object LoginError401 : ViewState()
    }

}