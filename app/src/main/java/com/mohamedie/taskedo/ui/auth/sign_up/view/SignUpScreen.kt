package com.mohamedie.taskedo.ui.auth.sign_up.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.auth.AuthGraph
import com.mohamedie.taskedo.ui.auth.sign_up.SignUpViewModel
import com.mohamedie.taskedo.ui.auth.sign_up.view.event.SignUpUIEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    navigateTo: (String, NavOptions?) -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.onEach {
            when (it) {
                SignUpUIEvent.NavigateToHome -> {
                    Toast.makeText(context, R.string.sign_in_successful, Toast.LENGTH_SHORT).show()
                    navigateTo(Graph.HOME, navOptions { popUpTo(Graph.AUTH) { inclusive = true } })
                }

                SignUpUIEvent.NavigateToVerifyEmail ->
                    navigateTo(
                        AuthGraph.VERIFY_EMAIL,
                        navOptions { popUpTo(Graph.AUTH) { inclusive = true } })
            }
        }.launchIn(this)
    }

    SignUpScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        navigateTo = { navigateTo(it, navOptions { popUpTo(Graph.AUTH) { inclusive = true } }) }
    )

}
