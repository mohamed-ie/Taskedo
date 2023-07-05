package com.mohamedie.taskedo.ui.auth.sign_in.view.event

sealed interface SignInUIEvent {
    object NavigateToHome:SignInUIEvent
    object NavigateToCompleteUserInfo:SignInUIEvent
    object NavigateToVerifyEmail:SignInUIEvent
}