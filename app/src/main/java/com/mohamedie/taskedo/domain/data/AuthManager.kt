package com.mohamedie.taskedo.domain.data

import com.mohamedie.taskedo.utils.Resource

interface AuthManager {

    suspend fun signUp(email: String, password: String): Resource<Boolean>
    suspend fun signUpWithGoogle(idToken: String): Resource<Boolean>
    suspend fun sendEmailVerification(): Resource<Void>

    /**
     * return pair of isMailVerified and isUserInfoCompleted
     */
    suspend fun signIn(email: String, password: String): Resource<Pair<Boolean, Boolean>>
    suspend fun sendRestPasswordEmail(email: String):Resource<Void>
    fun signOut()
}
