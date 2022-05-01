package com.wj.ut

import android.text.Editable
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * created by wenjing.liu at 2022/4/30
 */
/**
 * @ExperimentalCoroutinesApi 协程的启动模式
 * DEFAULT： 立即执行协程体，随时可以取消
 * LAZY：只有在用户需要的情况下运行
 * @ExperimentalCoroutinesApi
 * ATOMIC：立即执行协程体，但在开始运行协程体之前无法取消
 * @ExperimentalCoroutinesApi
 * UNDISPATCHED：立即执行协程体，直到第一个suspend函数调用
 * */
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    //为androidx.arch.core:core-testing
    //含有两个Junit规则：InstantTaskExecutorRule：强制架构组件立即执行调用线程上但任何后台操作
    //CountingTaskExecutorRule:用于Instrumentation测试，等待架构组件的后台操作或将其连接到Espresso作为闲置资源
    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: LoginViewModel
    private lateinit var dispatcher: CoroutineDispatcher

    @MockK
    private lateinit var api: HttpApi

    @MockK
    lateinit var observerViewState: Observer<LoginViewModel.ViewState>

    @MockK
    private lateinit var userEditable: Editable

    private lateinit var passwordEditable: Editable

    @Before
    fun start() {
        MockKAnnotations.init(this)
        passwordEditable = mockk()
        dispatcher = TestCoroutineDispatcher()
        Dispatchers.setMain(dispatcher)
        viewModel = LoginViewModel(api, dispatcher)
        every {
            observerViewState.onChanged(any())
        } returns Unit
    }

    @Test
    fun testVipUser() {
        every {
            userEditable.toString()
        } returns "vip"
        every {
            passwordEditable.toString()
        } returns "123456"
        viewModel.login(userEditable, passwordEditable)
        assert(viewModel.getViewState.value is LoginViewModel.ViewState.VipUser)
    }

    @Test
    fun testLoginSuccess() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        } returns "123456"

        runBlockingTest(dispatcher) {
            coEvery {
                api.login("123", "123456")
            } returns 0
            println("$userEditable , $passwordEditable")
            viewModel.login(userEditable, passwordEditable)
            assert(viewModel.getViewState.value is LoginViewModel.ViewState.LoginSuccess)
            coVerify {
                api.login("123", "123456")
            }
        }
    }

    @Test
    fun testLoginFailure() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        } returns "12345678"
        runBlockingTest(dispatcher) {
            coEvery {
                api.login(userEditable.toString(), passwordEditable.toString())
            } returns 1
            viewModel.login(userEditable, passwordEditable)
            assert(viewModel.getViewState.value is LoginViewModel.ViewState.LoginFailure)
        }
        coVerify {
            api.login(userEditable.toString(), passwordEditable.toString())
        }
    }

    @Test
    fun testLoginError401() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        } returns "123"
        runBlockingTest(dispatcher) {
            coEvery {
                api.login(userEditable.toString(), passwordEditable.toString())
            } returns 401
            viewModel.login(userEditable, passwordEditable)
            assert(viewModel.getViewState.value is LoginViewModel.ViewState.LoginError401)
        }
        coVerify {
            api.login(userEditable.toString(), passwordEditable.toString())
        }
    }

    @Test
    fun testLoginInvalidPassword() {
        every {
            userEditable.toString()
        } returns "123"
        every {
            passwordEditable.toString()
        } returns "12323243"
        runBlockingTest(dispatcher) {
            coEvery {
                api.login(userEditable.toString(), passwordEditable.toString())
            } returns 10000
            viewModel.login(userEditable, passwordEditable)
            assert(viewModel.getViewState.value is LoginViewModel.ViewState.InvalidPassword)
        }
        coVerify {
            api.login(userEditable.toString(), passwordEditable.toString())
        }
    }

    @After
    fun end() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}