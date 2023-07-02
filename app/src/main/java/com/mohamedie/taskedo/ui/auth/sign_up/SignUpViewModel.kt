package com.mohamedie.taskedo.ui.auth.sign_up

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.mohamedie.taskedo.domain.data.AuthManager
import com.mohamedie.taskedo.domain.helpers.TextFieldValidator
import com.mohamedie.taskedo.ui.auth.sign_up.view.event.SignUpEvent
import com.mohamedie.taskedo.ui.auth.sign_up.view.event.SignUpUIEvent
import com.mohamedie.taskedo.ui.auth.sign_up.view.state.SignUpState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignUpViewModel(
    private val authManager: AuthManager,
    private val textFieldValidator: TextFieldValidator
) : ViewModel() {
    var state by mutableStateOf(SignUpState())
        private set

    private val _uiEvent = MutableSharedFlow<SignUpUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged ->
                changeEmailValue(event.value)

            is SignUpEvent.PasswordChanged ->
                changePasswordValue(event.value)

            SignUpEvent.TogglePasswordVisibility ->
                togglePasswordVisibility()

            is SignUpEvent.ConfirmPasswordChanged ->
                changeConfirmPasswordValue(event.value)

            SignUpEvent.ToggleConfirmPasswordVisibility ->
                toggleConfirmPasswordVisibility()

            SignUpEvent.CreateAccount ->
                if (isDataValid())
                    createAccount()

            is SignUpEvent.OnSignInWithGoogleResult ->
                signUpWithGoogle(event.result)
        }
    }

    private fun changeEmailValue(value: String) {
        val email = state.email.copy(value = value)
        state = state.copy(email = email)
    }

    private fun changePasswordValue(value: String) {
        val password = state.password.copy(value = value)
        state = state.copy(password = password)
    }

    private fun togglePasswordVisibility() {
        val isVisible = state.password.isTrailingIconVisible.not()
        val password = state.password.copy(isTrailingIconVisible = isVisible)
        state = state.copy(password = password)
    }

    private fun changeConfirmPasswordValue(value: String) {
        val confirmPassword = state.confirmPassword.copy(value = value)
        state = state.copy(confirmPassword = confirmPassword)
    }

    private fun toggleConfirmPasswordVisibility() {
        val isVisible = state.confirmPassword.isTrailingIconVisible.not()
        val confirmPassword = state.confirmPassword.copy(isTrailingIconVisible = isVisible)
        state = state.copy(confirmPassword = confirmPassword)
    }

    private fun isDataValid(): Boolean {
        validateInputs()
        return with(state) { !email.isError && !password.isError && !confirmPassword.isError }
    }

    private fun validateInputs() {
        val email = textFieldValidator.validateEmail(state.email)
        val password = textFieldValidator.validatePassword(state.password)
        val confirmPassword =
            textFieldValidator.validateConfirmPassword(state.confirmPassword, state.password.value)

        state = state.copy(email = email, password = password, confirmPassword = confirmPassword)
    }

    private fun createAccount() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            authManager.signUp(state.email.value, state.password.value)
                .handle(onError = { state = state.copy(remoteError = it) },
                    onSuccess = { _uiEvent.emit(SignUpUIEvent.NavigateToVerifyEmail) })
            state = state.copy(isLoading = false)
        }
    }

    private fun signUpWithGoogle(result: Task<GoogleSignInAccount>?) = viewModelScope.launch {
        val idToken = result?.await()?.idToken ?: return@launch
        authManager.signUpWithGoogle(idToken)
            .handle(
                onError = { state = state.copy(remoteError = it) },
                onSuccess = { _uiEvent.emit(SignUpUIEvent.NavigateToHome) }
            )
    }

}