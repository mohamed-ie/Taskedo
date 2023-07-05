package com.mohamedie.taskedo.ui.landing

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.landing.splash.view.SplashScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.landingGraph(navController: NavController) {
    navigation(route = Graph.LANDING, startDestination = LandingGraph.SPLASH) {
        composable(route = LandingGraph.SPLASH) {
            SplashScreen(viewModel = koinViewModel(), navController::navigate)
        }
    }
}

object LandingGraph {
    const val SPLASH = "SPLASH"
}