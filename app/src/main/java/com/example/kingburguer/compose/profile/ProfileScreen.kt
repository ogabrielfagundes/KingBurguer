package com.example.kingburguer.compose.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(start = 20.dp, end = 20.dp, top = 30.dp)
        ) {
            ProfileProperty(R.string.prop_id, 3)
            ProfileProperty(R.string.prop_name, "User A")
            ProfileProperty(R.string.prop_email, "usera@gmail.com")
            ProfileProperty(R.string.prop_document, "111.222.333-44")
            ProfileProperty(R.string.prop_birthday, "02/02/2000")
        }
    }
}

@Composable
private fun ProfileProperty(@StringRes key: Int, value: Any) {
    Column {

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(key),
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = value.toString(),
                color = MaterialTheme.colorScheme.inverseSurface
            )
        }
        Divider(
            modifier = Modifier.padding(vertical = 14.dp),
            thickness = 0.8.dp
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LightProfileScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = false) {
        ProfileScreen()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DarkProfileScreenPreview() {
    KingBurguerTheme(dynamicColor = false, darkTheme = true) {
        ProfileScreen()
    }
}