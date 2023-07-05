package com.mohamedie.taskedo.ui.auth.verify_email.view.event

sealed interface VerifyEmailEvent {
    object ResendVerificationEmail : VerifyEmailEvent
    object ChangeEmail : VerifyEmailEvent
}