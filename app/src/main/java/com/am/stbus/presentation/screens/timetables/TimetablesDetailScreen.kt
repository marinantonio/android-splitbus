package com.am.stbus.presentation.screens.timetables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.presentation.screens.BaseScreen
import com.am.stbus.presentation.theme.SplitBusTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TimetablesDetailWithToolbar(
    viewModel: TimetablesDetailViewModel = koinViewModel(),
    timetable: Timetable
) {
    LaunchedEffect(Unit) {
        viewModel.getTimetableData()
    }

    val timetableData = viewModel.timetableData

    BaseScreen(
        title = "Home"
    ) {
        TimetablesDetailScreen(
            timetable,
            timetableData
        )
    }
}

@Composable
fun TimetablesDetailScreen(
    timetable: Timetable,
    timetableData: String
) {
    Column {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(timetable.title),
        )
        Text(timetableData)
    }
    Spacer(modifier = Modifier.height(4.dp))
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