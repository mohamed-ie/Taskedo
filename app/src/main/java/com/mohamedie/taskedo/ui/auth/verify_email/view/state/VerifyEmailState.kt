package com.mohamedie.taskedo.ui.auth.verify_email.view.state

import com.mohamedie.taskedo.utils.UIText

data class VerifyEmailState(
    val email: String,
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
