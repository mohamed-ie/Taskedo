package com.mohamedie.taskedo.domain.data

import com.mohamedie.taskedo.utils.Resource

interface AuthManager {

    suspend fun signUp(email: String, password: String): Resource<Boolean>
    suspend fun signUpWithGoogle(idToken: String) : Resource<Boolean>
}
