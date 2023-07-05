package com.mohamedie.taskedo.data.user

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mohamedie.taskedo.domain.data.UserManager
import com.mohamedie.taskedo.utils.Resource
import com.mohamedie.taskedo.utils.awaitResource

class UserManagerImpl(
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
    private val store: FirebaseFirestore
) : UserManager {

    override val isSignedIn: Boolean
        get() = auth.currentUser != null

    override val isEmailVerified: Boolean
        get() = auth.currentUser?.isEmailVerified == true

    override suspend fun completeUserInfo(
        displayName: String,
        username: String,
        photoUri: Uri?
    ): Resource<Void> {
        val user = auth.currentUser ?: return Resource.Error()
        val photoPath = photoUri?.let {
            storage.getReference("users")
                .child(user.uid)
                .putFile(it)
                .awaitResource()
                .getOrNull()
                ?.metadata
                ?.reference
                ?.downloadUrl
                ?.awaitResource()
                ?.getOrNull()
        }

        val profileUpdates = userProfileChangeRequest {
            this.displayName = displayName
            if (photoUri != null)
                this.photoUri = photoPath
        }

        return user.updateProfile(profileUpdates)
            .awaitResource()
    }

    override suspend fun reloadUser(): Resource<Void>? {
        return auth.currentUser?.reload()
            ?.awaitResource()
    }
}