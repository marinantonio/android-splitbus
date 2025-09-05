package com.am.breweyexplorer.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.am.breweyexplorer.presentation.theme.BreweyExplorerTheme
import com.am.breweyexplorer.presentation.theme.PastelPurpleBg
import com.am.stbus.presentation.screens.BaseScreen

@Composable
fun HomeScreenWithToolbar() {
    BaseScreen(
        modifier = Modifier.background(PastelPurpleBg),
        title = "Home"
    ) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Test"
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    BreweyExplorerTheme {
        Row(
            modifier = Modifier
                .background(PastelPurpleBg)
                .fillMaxSize()
        ) {
            HomeScreen()
        }
    }
}