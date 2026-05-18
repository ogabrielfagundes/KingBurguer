package com.example.kingburguer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.kingburguer.compose.KingBurguerApp
import com.example.kingburguer.compose.KingBurguerNavHost
import com.example.kingburguer.compose.Screen
import com.example.kingburguer.ui.theme.KingBurguerTheme
import com.example.kingburguer.viewmodels.SplashViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels { SplashViewModel.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                Log.d("MainActivity", "keep on!!")
                viewModel.hasSession.value == null
            }
        }
        enableEdgeToEdge()
        setContent {
            KingBurguerTheme(dynamicColor = false) {
                val startState = viewModel.hasSession.collectAsState()
                Log.d("MainActivity", "compose started! $startState")
                startState.value?.let { logged ->
                    val startDestination = if (logged) Screen.MAIN else Screen.LOGIN
                    KingBurguerApp(startDestination)
                }
            }
        }
    }
}