package com.wj.ut

import android.text.Editable
import androidx.lifecycle.ViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * created by wenjing.liu at 2022/4/30
 */
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @MockK
    private lateinit var api: HttpApi

    @MockK
    private lateinit var userEditable: Editable

    @MockK
    private lateinit var passwordEditable: Editable

    @Before
    fun start() {
        MockKAnnotations.init(this)
        //TODO
        val dispatcher = Dispatchers.IO
        viewModel = LoginViewModel(api, dispatcher)

    }

    @Test
    fun testLoginSuccess() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        }returns "123456"

    }

    @Test
    fun testLoginFailure() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        }returns "12345678"

    }

    @Test
    fun testLoginError401() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        }returns "123"

    }

    @Test
    fun testLoginInvalidPassword() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        }returns "12323243"

    }

    @After
    fun end() {

    }
}