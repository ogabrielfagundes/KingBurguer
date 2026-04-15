package com.example.kingburguer.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kingburguer.compose.home.HomeScreen
import com.example.kingburguer.compose.login.LoginScreen
import com.example.kingburguer.compose.singup.SignUpScreen
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingBurguerApp() {
    val navController = rememberNavController()
    KingBurguerNavHost(navController = navController)
}

@Composable
fun KingBurguerNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.LOGIN.route
    ) {
        composable(Screen.LOGIN.route) {
            LoginScreen(onSignUpClick = {
                navController.navigate(Screen.SIGNUP.route)
            }, onNavigateToHome = {
                navController.navigate(Screen.HOME.route) {
                    popUpTo(Screen.LOGIN.route) { inclusive = true }
                }
            })
        }
        composable(Screen.SIGNUP.route) {
            SignUpScreen(
                onNavigationClick = {
                    navController.navigateUp()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.HOME.route) {
                        popUpTo(Screen.LOGIN.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.HOME.route) {
            HomeScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KingBurguerAppPreview() {
    KingBurguerTheme {
        KingBurguerApp()
    }
}