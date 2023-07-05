package com.mohamedie.taskedo.domain.data

import android.net.Uri
import com.google.firebase.auth.FirebaseUser
import com.mohamedie.taskedo.utils.Resource

interface UserManager {
    val isSignedIn: Boolean
    val isEmailVerified: Boolean
    suspend fun completeUserInfo(
        displayName: String,
        username: String,
        photoUri: Uri? = null
    ): Resource<Void>

    suspend fun reloadUser(): Resource<Void>?
}