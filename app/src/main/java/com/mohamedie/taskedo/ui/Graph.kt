package com.mohamedie.taskedo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mohamedie.taskedo.ui.auth.authGraph
import com.mohamedie.taskedo.ui.home.homeGraph

@Composable
fun AppGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        route = Graph.ROOT,
        startDestination = Graph.AUTH
    ) {
        authGraph(navHostController)
        homeGraph(navHostController)
    }
}

object Graph {
    const val ROOT = "ROOT_GRAPH"
    const val LANDING = "LANDING_GRAPH"
    const val AUTH = "AUTH-GRAPH"
    const val HOME = "HOME-GRAPH"
}