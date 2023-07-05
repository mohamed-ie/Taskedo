package com.mohamedie.taskedo.ui.auth.complete_user_info.view.event

import android.net.Uri

sealed interface CompleteUserInfoEvent {
    object Save : CompleteUserInfoEvent

    class DisplayNameChanged(val value: String) : CompleteUserInfoEvent
    class UserNameChanged(val value: String) : CompleteUserInfoEvent
    class PhotoUriChanged(val value: Uri?) : CompleteUserInfoEvent


}
