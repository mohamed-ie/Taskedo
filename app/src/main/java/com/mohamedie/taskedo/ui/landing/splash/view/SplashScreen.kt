package com.mohamedie.taskedo.ui.landing.splash.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.mohamedie.taskedo.R
import com.mohamedie.taskedo.ui.common.dialog.TaskedoAlertDialog
import com.mohamedie.taskedo.ui.landing.LandingGraph
import com.mohamedie.taskedo.ui.landing.splash.SplashViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.system.exitProcess

@Composable
fun SplashScreen(viewModel: SplashViewModel, navigateTo: (String, NavOptions?) -> Unit) {

    LaunchedEffect(Unit) {
        viewModel.destination.onEach {
            navigateTo(it, navOptions { popUpTo(LandingGraph.SPLASH) { inclusive = true } })
        }.launchIn(this)
    }

    SplashScreenContent()

    TaskedoAlertDialog(
        visible = viewModel.remoteError != null,
        message = viewModel.remoteError?.asString(),
        confirmMessage = stringResource(id = R.string.retry),
        cancelMessage = stringResource(id = R.string.exit),
        onConfirm = viewModel::reloadUser,
        onCancel = { exitProcess(-1) }
    )
}
