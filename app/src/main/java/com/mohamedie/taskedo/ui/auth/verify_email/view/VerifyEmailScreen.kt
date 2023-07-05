package com.mohamedie.taskedo.ui.auth.verify_email.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.auth.verify_email.VerifyEmailViewModel
import com.mohamedie.taskedo.ui.auth.verify_email.view.event.VerifyEmailUIEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun VerifyEmailScreen(viewModel: VerifyEmailViewModel, navigateTo: (String, NavOptions?) -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.onEach {
            when (it) {
                VerifyEmailUIEvent.NavigateToSignIn ->
                    navigateTo(Graph.AUTH, navOptions { popUpTo(Graph.AUTH) { inclusive = true } })

                VerifyEmailUIEvent.ShowEmailSentToast ->
                    Toast.makeText(context, R.string.verification_email_sent, Toast.LENGTH_SHORT)
                        .show()

            }
        }.launchIn(this)
    }

    VerifyEmailScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}