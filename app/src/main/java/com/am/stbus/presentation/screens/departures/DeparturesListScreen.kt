package com.am.stbus.presentation.screens.departures

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.presentation.theme.SplitBusTheme
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun DeparturesScreen(
    viewModel: DeparturesListViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        Timber.d("Debugging - test123")
        viewModel.getTimetableData()
    }

    val timetableData = viewModel.timetableData

    Column {
        timetableData.forEach {
            Text(it.transportServiceRouteCode ?: "")
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}


@Preview
@Composable
fun TimetablesDetailScreenPreview() {
    SplitBusTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            //TimetablesDetailScreen()
        }
    }
}