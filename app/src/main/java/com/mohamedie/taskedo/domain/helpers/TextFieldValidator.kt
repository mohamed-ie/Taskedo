package com.mohamedie.taskedo.domain.helpers

import com.mohamedie.taskedo.ui.common.state.TextFieldState

interface TextFieldValidator {
    fun validateEmail(state: TextFieldState): TextFieldState
    fun validatePassword(state: TextFieldState): TextFieldState
    fun validateConfirmPassword(
        state: TextFieldState,
        password: String
    ): TextFieldState
}