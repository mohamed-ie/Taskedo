package com.mohamedie.taskedo.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mohamedie.taskedo.domain.data.AuthManager
import com.mohamedie.taskedo.utils.Resource
import com.mohamedie.taskedo.utils.awaitResource

class FirebaseAuthManagerImpl(
    private val auth: FirebaseAuth
) : AuthManager {
    override suspend fun signUp(email: String, password: String): Resource<Boolean> {
        return auth.createUserWithEmailAndPassword(email, password)
            .awaitResource()
            .map { it.user?.isEmailVerified == true }
    }

    override suspend fun signUpWithGoogle(idToken: String): Resource<Boolean> {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(firebaseCredential)
            .awaitResource()
            .map { it.additionalUserInfo?.isNewUser == true }
    }
}
