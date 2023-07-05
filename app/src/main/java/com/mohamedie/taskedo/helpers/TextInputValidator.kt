package com.mohamedie.taskedo.helpers

import android.util.Patterns
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.domain.helpers.TextFieldValidator
import com.mohamedie.taskedo.ui.common.state.TextFieldState
import com.mohamedie.taskedo.utils.UIText

class TextFieldValidatorImpl : TextFieldValidator {
    private val usernameRegex by lazy { Regex("^[a-zA-z]{3,}#[0-9]{3,}\$") }
    override fun validateEmail(state: TextFieldState,error:UIText): TextFieldState {
        return state.emptyValidation {
            val isError = !Patterns.EMAIL_ADDRESS.matcher(state.value).matches()
            val error = if (isError) error else null
            state.copy(isError = isError, error = error)
        }
    }

    override fun validatePassword(state: TextFieldState): TextFieldState {
        return state.emptyValidation {
            val isError = state.value.length < 6
            val error =
                if (isError) UIText.StringResource(R.string.your_password_must_be_at_least_6_characters)
                else null
            state.copy(isError = isError, error = error)
        }
    }

    override fun validateConfirmPassword(
        state: TextFieldState,
        password: String
    ): TextFieldState {
        return state.emptyValidation {
            val isError = state.value != password
            val error = if (isError) UIText.StringResource(R.string.passwords_dont_match)
            else null
            state.copy(isError = isError, error = error)
        }
    }

    override fun emptyValidation(state: TextFieldState): TextFieldState {
        return state.emptyValidation { state.copy(isError = false, error = null) }
    }

    override fun validateUsername(state: TextFieldState): TextFieldState {
        return state.emptyValidation {
            val isError = !usernameRegex.matches(state.value)
            val error = if (isError) UIText.StringResource(R.string.username_error)
            else null
            state.copy(isError = isError, error = error)
        }
    }

    private fun TextFieldState.emptyValidation(then: () -> TextFieldState): TextFieldState {
        return if (value.isBlank())
            copy(isError = true, error = UIText.StringResource(R.string.this_field_is_required))
        else
            then()
    }
}