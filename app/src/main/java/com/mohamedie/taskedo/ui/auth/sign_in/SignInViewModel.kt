package com.mohamedie.taskedo.ui.auth.sign_in

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.mohamedie.taskedo.domain.data.AuthManager
import com.mohamedie.taskedo.domain.helpers.TextFieldValidator
import com.mohamedie.taskedo.ui.auth.sign_in.view.event.SignInEvent
import com.mohamedie.taskedo.ui.auth.sign_in.view.event.SignInUIEvent
import com.mohamedie.taskedo.ui.auth.sign_in.view.state.SignInState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SignInViewModel(
    private val textFieldValidator: TextFieldValidator,
    private val authManager: AuthManager
) : ViewModel() {
    var state by mutableStateOf(SignInState())
        private set

    private val _uiEvent = MutableSharedFlow<SignInUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.EmailChanged ->
                changeEmailValue(event.value)

            is SignInEvent.PasswordChanged ->
                changePasswordValue(event.value)

            SignInEvent.TogglePasswordVisibility ->
                togglePasswordVisibility()

            SignInEvent.SendRestPasswordEmail ->
                if (isEmailValid())
                    sendRestPasswordEmail()

            SignInEvent.HideRestPasswordDialog ->
                state = state.copy(isRestPasswordDialogVisible = false)

            SignInEvent.SignIn ->
                if (isDataValid())
                    signIn()

            is SignInEvent.OnSignInWithGoogleResult ->
                signInWithGoogle(event.result)

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

    private fun isEmailValid(): Boolean {
        validateEmail()
        return !state.email.isError
    }

    private fun validateEmail() {
        val email = textFieldValidator.validateEmail(state.email)
        state = state.copy(email = email)
    }


    private fun sendRestPasswordEmail() = viewModelScope.launch {
        authManager.sendRestPasswordEmail(state.email.value)
            .handle(
                onError = { state = state.copy(remoteError = it) },
                onSuccess = { state = state.copy(isRestPasswordDialogVisible = true) }
            )
    }

    private fun isDataValid(): Boolean {
        validateInputs()
        return with(state) { !email.isError && !password.isError }
    }

    private fun validateInputs() {
        val email = textFieldValidator.emptyValidation(state.email)
        val password = textFieldValidator.emptyValidation(state.password)

        state = state.copy(email = email, password = password)
    }

    private fun signIn() = viewModelScope.launch {
        state = state.copy(isLoading = true, remoteError = null)
        authManager.signIn(state.email.value, state.password.value)
            .handle(
                onError = { state = state.copy(remoteError = it) },
                onSuccess = { pair ->
                    //isMailVerified
                    if (pair.first)
                        _uiEvent.emit(SignInUIEvent.NavigateToVerifyEmail)
                    //isUserInfoCompleted
                    else if (pair.second)
                        _uiEvent.emit(SignInUIEvent.NavigateToCompleteUserInfo)
                }
            )
        state = state.copy(isLoading = false)
    }


    private fun signInWithGoogle(result: Task<GoogleSignInAccount>?) = viewModelScope.launch {
        state = state.copy(remoteError = null)
        val idToken = result?.await()?.idToken ?: return@launch
        authManager.signUpWithGoogle(idToken)
            .handle(
                onError = { state = state.copy(remoteError = it) },
                onSuccess = { _uiEvent.emit(SignInUIEvent.NavigateToHome) }
            )
    }
}