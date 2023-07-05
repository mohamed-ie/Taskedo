package com.mohamedie.taskedo.ui.landing.splash

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedie.taskedo.domain.data.UserManager
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.auth.AuthGraph
import com.mohamedie.taskedo.utils.UIText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SplashViewModel(
    private val userManager: UserManager
) : ViewModel() {
    var remoteError by mutableStateOf<UIText?>(null)
        private set

    private val _destination = MutableSharedFlow<String>()
    val destination = _destination.asSharedFlow()


    init {
        reloadUser()
    }

    fun reloadUser() = viewModelScope.launch {
        userManager.reloadUser()
            ?.handle(
                onError = { remoteError = it },
                onSuccess = { _destination.emit(getDestination()) }
            ) ?: normalLoading()
    }

    private suspend fun normalLoading() {
        delay(2000)
        _destination.emit(Graph.AUTH)
    }

    private fun getDestination() =
        if (userManager.isSignedIn)
            if (userManager.isEmailVerified)
                Graph.HOME
            else
                AuthGraph.VERIFY_EMAIL
        else Graph.AUTH

}