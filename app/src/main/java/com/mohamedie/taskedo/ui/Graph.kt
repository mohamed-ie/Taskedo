package com.mohamedie.taskedo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mohamedie.taskedo.ui.auth.authGraph
import com.mohamedie.taskedo.ui.home.homeGraph
import com.mohamedie.taskedo.ui.landing.landingGraph

@Composable
fun AppGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.LANDING
    ) {
        landingGraph(navHostController)
        authGraph(navHostController)
        homeGraph(navHostController)
    }
}

object Graph {
    const val BACK = "BACK"
    const val ROOT = "ROOT_GRAPH"
    const val LANDING = "LANDING_GRAPH"
    const val AUTH = "AUTH-GRAPH"
    const val HOME = "HOME-GRAPH"
}