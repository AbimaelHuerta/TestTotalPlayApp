package com.example.pruebatotalplayapp

import com.example.pruebatotalplayapp.presentation.login.LoginViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
@OptIn(ExperimentalCoroutinesApi::class)
class LoginUnitTest {
    private  val testDispatcher= StandardTestDispatcher()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun estadoInicialVacio(){
        val state = viewModel.state.value

        assertEquals("", state.username)
        assertEquals("", state.password)
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertNull(state.errorMessage)
    }

    @Test
    fun credencialesCorrectas() = runTest{
        viewModel.onUsernameChange("admin")
        viewModel.onPasswordChange("admin")
        viewModel.login()

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertTrue(state.isSuccess)
        assertNull(state.errorMessage)

    }

    @Test
    fun credencialesIncorrectas() = runTest{
        viewModel.onUsernameChange("123")
        viewModel.onPasswordChange("123")
        viewModel.login()

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertFalse(state.isLoading)
        assertFalse(state.isSuccess)
        assertEquals("Usuario o contraseña incorrectos", state.errorMessage)

    }
}