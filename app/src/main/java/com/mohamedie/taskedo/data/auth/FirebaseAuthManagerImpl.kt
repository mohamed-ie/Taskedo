package com.mohamedie.taskedo.data.auth

import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mohamedie.taskedo.domain.data.AuthManager
import com.mohamedie.taskedo.utils.Resource
import com.mohamedie.taskedo.utils.awaitResource

class FirebaseAuthManagerImpl(
    private val auth: FirebaseAuth
) : AuthManager {
    private val actionCodeSettings by lazy {
        ActionCodeSettings.newBuilder()
        .setUrl("http://taskedo.com/")
        .setAndroidPackageName("com.mohamedie.taskedo", false, null)
        .build()
    }
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

    override suspend fun sendEmailVerification(): Resource<Void> {
        val user = auth.currentUser!!
        return user.sendEmailVerification(actionCodeSettings).awaitResource()
    }

    override suspend fun signIn(email: String, password: String): Resource<Pair<Boolean, Boolean>> {
        return auth.signInWithEmailAndPassword(email, password)
            .awaitResource()
            .map { Pair(it.user?.isEmailVerified == true, it.user?.displayName != null) }
    }

    override suspend fun sendRestPasswordEmail(email: String): Resource<Void> {
        return auth.sendPasswordResetEmail(email,actionCodeSettings)
            .awaitResource()
    }

    override  fun signOut() =
        auth.signOut()

}
