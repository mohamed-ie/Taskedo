package com.mohamedie.taskedo.ui.auth.verify_email

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mohamedie.taskedo.domain.data.AuthManager
import com.mohamedie.taskedo.ui.auth.verify_email.view.event.VerifyEmailEvent
import com.mohamedie.taskedo.ui.auth.verify_email.view.event.VerifyEmailUIEvent
import com.mohamedie.taskedo.ui.auth.verify_email.view.state.VerifyEmailState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    firebaseUser: FirebaseUser,
    private val authManager: AuthManager
) : ViewModel() {
    var state by mutableStateOf(VerifyEmailState(firebaseUser.email ?: ""))
        private set

    private val _uiEvent = MutableSharedFlow<VerifyEmailUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        sendEmailVerification()
    }

    fun onEvent(event: VerifyEmailEvent) {
        when (event) {
            VerifyEmailEvent.ChangeEmail ->
                signOut()

            VerifyEmailEvent.ResendVerificationEmail ->
                sendEmailVerification()
        }
    }

    private fun signOut() =viewModelScope.launch{
        authManager.signOut()
        _uiEvent.emit(VerifyEmailUIEvent.NavigateToSignIn)
    }


    private fun sendEmailVerification() = viewModelScope.launch {
        state = state.copy(isLoading = true)
        authManager.sendEmailVerification()
            .handle(
                onError = { state = state.copy(remoteError = it) },
                onSuccess = { _uiEvent.emit(VerifyEmailUIEvent.ShowEmailSentToast) }
            )
        state = state.copy(isLoading = false)
    }


}