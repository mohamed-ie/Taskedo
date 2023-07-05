package com.mohamedie.taskedo.ui.auth.sign_in.view.event

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task

sealed interface SignInEvent {
    object TogglePasswordVisibility : SignInEvent
    object SignIn : SignInEvent
    object SendRestPasswordEmail : SignInEvent
    object HideRestPasswordDialog : SignInEvent

    class EmailChanged(val value: String) : SignInEvent
    class PasswordChanged(val value: String) : SignInEvent
    class OnSignInWithGoogleResult(val result: Task<GoogleSignInAccount>?) : SignInEvent
}