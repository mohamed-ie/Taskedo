package com.mohamedie.taskedo.ui.auth.sign_up.view.state

import com.mohamedie.taskedo.ui.common.state.TextFieldState
import com.mohamedie.taskedo.utils.UIText

data class SignUpState(
    val email: TextFieldState = TextFieldState(),
    val password: TextFieldState = TextFieldState(),
    val confirmPassword: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
