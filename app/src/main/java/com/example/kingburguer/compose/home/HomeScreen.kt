package com.example.kingburguer.compose.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kingburguer.ui.theme.KingBurguerTheme
@Composable
fun HomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {}
        ) { contentPadding ->
            HomeContentScreen(modifier = Modifier.padding(contentPadding.calculateTopPadding()))
        }
    }
}

@Composable
private fun HomeContentScreen(
    modifier: Modifier
) {
    Surface(modifier = modifier) {
        Text("Home")
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightHomeScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        HomeScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkLHomeScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        HomeScreen()
    }
}