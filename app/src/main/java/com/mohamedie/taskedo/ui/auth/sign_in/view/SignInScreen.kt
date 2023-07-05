package com.mohamedie.taskedo.ui.auth.sign_in.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.auth.AuthGraph
import com.mohamedie.taskedo.ui.auth.sign_in.SignInViewModel
import com.mohamedie.taskedo.ui.auth.sign_in.view.component.RestPasswordDialog
import com.mohamedie.taskedo.ui.auth.sign_in.view.event.SignInEvent
import com.mohamedie.taskedo.ui.auth.sign_in.view.event.SignInUIEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInScreen(viewModel: SignInViewModel, navigateTo: (String, NavOptions?) -> Unit) {
    val context = LocalContext.current
    val state = viewModel.state
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.onEach {
            when (it) {
                SignInUIEvent.NavigateToCompleteUserInfo ->
                    navigateTo(
                        AuthGraph.COMPLETE_USER_INFO,
                        navOptions { popUpTo(Graph.AUTH) { inclusive = true } }
                    )

                SignInUIEvent.NavigateToHome -> {
                    Toast.makeText(context, R.string.sign_in_successful, Toast.LENGTH_SHORT).show()
                    navigateTo(
                        Graph.HOME,
                        navOptions { popUpTo(Graph.AUTH) { inclusive = true } }
                    )
                }

                SignInUIEvent.NavigateToVerifyEmail ->
                    navigateTo(
                        AuthGraph.VERIFY_EMAIL,
                        navOptions { popUpTo(Graph.AUTH) { inclusive = true } }
                    )
            }
        }.launchIn(this)
    }

    SignInScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        navigateTo = { navigateTo(it, navOptions { popUpTo(Graph.AUTH) { inclusive = true } }) }

    )

    RestPasswordDialog(
        visible = state.isRestPasswordDialogVisible,
        email = state.email.value,
        dismiss = { viewModel.onEvent(SignInEvent.HideRestPasswordDialog) }
    )
}