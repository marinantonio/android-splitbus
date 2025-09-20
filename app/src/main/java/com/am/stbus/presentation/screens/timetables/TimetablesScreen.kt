package com.am.stbus.presentation.screens.timetables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.R
import com.am.stbus.presentation.screens.BaseScreen
import com.am.stbus.presentation.theme.SplitBusTheme

@Composable
fun TimetablesWithToolbar(
    onTimetableClick: (Timetable) -> Unit
) {
    BaseScreen(
        title = "Home"
    ) {
        TimetablesScreen(
            onTimetableClick
        )
    }
}

@Composable
fun TimetablesScreen(
    onTimetableClick: (Timetable) -> Unit
) {

    val listOfTimetables = listOf(
        Timetable(
            id = 1,
            title = R.string.gmaps_route_11,
            titleWebsite = "Test"
        ),
        Timetable(
            id = 1,
            title = R.string.gmaps_route_31,
            titleWebsite = "Test"
        )
    )

    Column {
        listOfTimetables.forEach {
            Card(
                modifier = Modifier
                    .clickable {
                        onTimetableClick(it)
                    }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = stringResource(it.title),
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

data class Timetable(
    val id: Int,
    val title: Int,
    val titleWebsite: String
)

@Preview
@Composable
fun TimetablesScreenPreview() {
    SplitBusTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TimetablesScreen {}
        }
    }
}