package com.mohamedie.taskedo.ui.auth.sign_up.view.event

interface SignUpUIEvent {
    object NavigateToHome : SignUpUIEvent
    object NavigateToVerifyEmail : SignUpUIEvent
}