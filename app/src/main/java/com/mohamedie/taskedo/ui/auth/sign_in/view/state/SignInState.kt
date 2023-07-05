package com.mohamedie.taskedo.ui.auth.sign_in.view.state

import com.mohamedie.taskedo.ui.common.state.TextFieldState
import com.mohamedie.taskedo.utils.UIText

data class SignInState(
    val email: TextFieldState = TextFieldState(""),
    val password: TextFieldState = TextFieldState(""),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false,
    val isRestPasswordDialogVisible: Boolean =false,
    val isVerifyEmailDialogVisible: Boolean =false
)
