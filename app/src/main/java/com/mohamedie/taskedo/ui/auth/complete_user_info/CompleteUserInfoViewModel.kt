package com.mohamedie.taskedo.ui.auth.complete_user_info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedie.taskedo.domain.data.UserManager
import com.mohamedie.taskedo.domain.helpers.TextFieldValidator
import com.mohamedie.taskedo.ui.auth.complete_user_info.view.event.CompleteUserInfoEvent
import com.mohamedie.taskedo.ui.auth.complete_user_info.view.state.CompleteUserInfoState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CompleteUserInfoViewModel(
    private val textFieldValidator: TextFieldValidator,
    private val userManager: UserManager
) : ViewModel() {
    var state by mutableStateOf(CompleteUserInfoState())
        private set

    private val _navigateToHome = MutableSharedFlow<Unit>()
    val navigateToHome = _navigateToHome.asSharedFlow()

    fun onEvent(event: CompleteUserInfoEvent) {
        when (event) {
            is CompleteUserInfoEvent.PhotoUriChanged ->
                state = state.copy(photoUri = event.value)

            is CompleteUserInfoEvent.DisplayNameChanged ->
                changeDisplayNameValue(event.value)

            is CompleteUserInfoEvent.UserNameChanged ->
                changeUsernameValue(event.value)

            CompleteUserInfoEvent.Save ->
                if (isDataValid())
                    save()
        }
    }


    private fun changeDisplayNameValue(value: String) {
        val displayName = state.displayName.copy(value = value)
        state = state.copy(displayName = displayName)
    }

    private fun changeUsernameValue(value: String) {
        val username = state.username.copy(value = value)
        state = state.copy(username = username)
    }

    private fun isDataValid(): Boolean {
        validateInputs()
        return !state.displayName.isError && !state.username.isError
    }

    private fun validateInputs() {
        val displayName = textFieldValidator.emptyValidation(state.displayName)
        val username = textFieldValidator.validateUsername(state.username)
        state = state.copy(displayName = displayName, username = username)
    }


    private fun save() = viewModelScope.launch {
        state = state.copy(isLoading = true, remoteError = null)
        userManager.completeUserInfo(state.displayName.value, state.username.value, state.photoUri)
            .handle(
                onError = { state = state.copy(remoteError = it) },
                onSuccess = { _navigateToHome.emit(Unit) }
            )
        state = state.copy(isLoading = false)
    }

}