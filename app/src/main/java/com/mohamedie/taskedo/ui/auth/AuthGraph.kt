package com.mohamedie.taskedo.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.mohamedie.taskedo.ui.Graph
import com.mohamedie.taskedo.ui.auth.sign_up.view.SignUpScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(
        route = Graph.AUTH,
        startDestination = AuthGraph.SIGN_IN
    ) {
        composable(route = AuthGraph.SIGN_IN) {
            navController.navigate(AuthGraph.SIGN_UP)
        }

        composable(route = AuthGraph.SIGN_UP) {
            SignUpScreen(koinViewModel(), navController::navigate)
        }

        composable(route= AuthGraph.VERIFY_EMAIL){

        }

        composable(route=AuthGraph.COMPLETE_USER_INFO){

        }
    }

}

object AuthGraph {
    const val VERIFY_EMAIL = "VERIFY_EMAIL"
    const val COMPLETE_USER_INFO = "COMPLETE_USER_INFO"
    const val SIGN_IN = "SIGN_IN"
    const val SIGN_UP = "SIGN_UP"
}