package com.example.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.compose.login.LoginScreen
import com.example.kingburguer.compose.singup.SignUpScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.SplashViewModel

@Composable
fun KingBurguerApp(startDestination: Screen) {
    val navController = rememberNavController()
    KingBurguerNavHost(navController, startDestination)
}

@Composable
fun KingBurguerNavHost(navController: NavHostController, startDestination: Screen) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(Screen.LOGIN.route) {
            LoginScreen(onSignUpClick = {
                navController.navigate(Screen.SIGNUP.route)
            }, onNavigateToHome = {
                navController.navigate(Screen.MAIN.route) {
                    popUpTo(Screen.LOGIN.route) { inclusive = true }
                }
            })
        }
        composable(Screen.SIGNUP.route) {
            SignUpScreen(
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToLogin = {
                    navController.navigateUp()
                }
            )
        }
        composable(Screen.MAIN.route) {
            MainScreen(onNavigateToLogin = {
                navController.navigate(Screen.LOGIN.route) {
                    popUpTo(Screen.MAIN.route) { inclusive = true}
                }
            })
        }
    }
}