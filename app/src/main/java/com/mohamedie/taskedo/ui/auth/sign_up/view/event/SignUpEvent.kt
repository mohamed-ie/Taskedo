package com.mohamedie.taskedo.ui.auth.sign_up.view.event

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed interface SignUpEvent {
    object TogglePasswordVisibility : SignUpEvent
    object CreateAccount : SignUpEvent
    object ToggleConfirmPasswordVisibility : SignUpEvent

    class EmailChanged(val value: String) : SignUpEvent
    class PasswordChanged(val value: String) : SignUpEvent
    class ConfirmPasswordChanged(val value: String) : SignUpEvent
    class OnSignInWithGoogleResult(val result: Task<GoogleSignInAccount>?) : SignUpEvent
}