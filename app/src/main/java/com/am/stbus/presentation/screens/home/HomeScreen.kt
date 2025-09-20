package com.am.stbus.presentation.screens.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.am.stbus.presentation.screens.BaseScreen
import com.am.stbus.presentation.theme.SplitBusTheme

@Composable
fun HomeScreenWithToolbar() {
    BaseScreen(
        title = "Timetables"
    ) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Timetables"
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    SplitBusTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HomeScreen()
        }
    }
}