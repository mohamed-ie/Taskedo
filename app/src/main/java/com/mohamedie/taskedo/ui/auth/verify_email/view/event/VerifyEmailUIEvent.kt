package com.mohamedie.taskedo.ui.auth.verify_email.view.event

sealed interface VerifyEmailUIEvent {
    object ShowEmailSentToast : VerifyEmailUIEvent
    object NavigateToSignIn : VerifyEmailUIEvent
}