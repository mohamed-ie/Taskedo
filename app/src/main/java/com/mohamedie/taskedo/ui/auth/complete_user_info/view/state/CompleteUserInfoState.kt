package com.mohamedie.taskedo.ui.auth.complete_user_info.view.state

import android.net.Uri
import com.mohamedie.taskedo.ui.common.state.TextFieldState
import com.mohamedie.taskedo.utils.UIText

data class CompleteUserInfoState(
    val photoUri: Uri? = null,
    val displayName: TextFieldState = TextFieldState(),
    val username: TextFieldState = TextFieldState(),
    val remoteError: UIText? = null,
    val isLoading: Boolean = false
)
