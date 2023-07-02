package com.mohamedie.taskedo.ui.common.state

import com.mohamedie.taskedo.utils.UIText

data class TextFieldState(
    val value: String = "",
    val isError: Boolean = false,
    val error: UIText? = null,
    val isTrailingIconVisible: Boolean = false
)