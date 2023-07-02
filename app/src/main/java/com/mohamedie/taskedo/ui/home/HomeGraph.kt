package com.mohamedie.taskedo.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mohamedie.taskedo.ui.Graph


fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(route = Graph.HOME, startDestination = HomeGraph.OVERVIEW) {
        composable(route = HomeGraph.OVERVIEW){

        }
    }
}

object HomeGraph {
    const val OVERVIEW = "OVERVIEW"
}