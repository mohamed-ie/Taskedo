package com.mohamedie.taskedo.ui.auth.complete_user_info.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.auth.complete_user_info.CompleteUserInfoViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun CompleteUserInfoScreen(
    viewModel: CompleteUserInfoViewModel,
    navigateTo: (String, NavOptions?) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.navigateToHome.onEach {
            navigateTo(Graph.HOME, navOptions { popUpTo(Graph.HOME) })
        }.launchIn(this)
    }

    CompleteUserInfoScreenContent(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )

}
