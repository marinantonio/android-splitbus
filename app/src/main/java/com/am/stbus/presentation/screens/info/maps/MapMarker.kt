/*
 * MIT License
 *
 * Copyright (c) 2013 - 2026 Antonio Marin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.am.stbus.presentation.screens.info.maps

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.presentation.theme.SplitBusTheme
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState

@Composable
fun BusMarker(
    state: MarkerState,
    busNumber: String
) {
    MarkerComposable(
        state = state,
        anchor = Offset(0.5f, 1f)
    ) {
        BusMarkerContent(busNumber = busNumber)
    }
}

@Composable
fun BusMarkerContent(
    busNumber: String
) {
    val pinColour = MaterialTheme.colorScheme.primaryContainer

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = busNumber,
            modifier = Modifier
                .size(42.dp)
                .background(pinColour, CircleShape)
                .wrapContentSize(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        // Bottom pin triangle
        Canvas(
            modifier = Modifier
                .offset(y = (-2).dp)
                .size(width = 16.dp, height = 14.dp)
        ) {
            val path = Path().apply {
                moveTo(size.width / 2f, size.height)
                lineTo(0f, 0f)
                lineTo(size.width, 0f)
                close()
            }
            drawPath(path, color = pinColour)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusMarkerPreview() {
    SplitBusTheme {
        BusMarkerContent(busNumber = "37")
    }
}
