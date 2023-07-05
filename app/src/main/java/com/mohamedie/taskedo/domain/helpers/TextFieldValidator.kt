package com.mohamedie.taskedo.domain.helpers

import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.common.state.TextFieldState
import com.mohamedie.taskedo.utils.UIText

interface TextFieldValidator {
    fun validateEmail(
        state: TextFieldState,
        error: UIText = UIText.StringResource(R.string.please_enter_valid_email)
    ): TextFieldState

    fun validatePassword(state: TextFieldState): TextFieldState
    fun validateConfirmPassword(
        state: TextFieldState,
        password: String
    ): TextFieldState

    fun emptyValidation(state: TextFieldState): TextFieldState
    fun validateUsername(state: TextFieldState): TextFieldState
}