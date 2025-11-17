/*
 * MIT License
 *
 * Copyright (c) 2013 - 2025 Antonio Marin
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

package com.am.stbus.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.presentation.theme.SplitBusTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoadingIndicator(
            modifier = Modifier
                .scale(4f)
                .padding(top = 24.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary,
            polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons
        )

        CircularWavyProgressIndicator(
            modifier = Modifier
                .scale(7f)
                .padding(top = 64.dp, start = 8.dp, end = 0.dp)
                .align(Alignment.CenterEnd),
            color = MaterialTheme.colorScheme.primary,
            stroke = Stroke(width = 0.3f),
            amplitude = 1f
        )

        LoadingIndicator(
            modifier = Modifier
                .scale(2.5f)
                .padding(end = 64.dp, top = 148.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.tertiary,
            polygons = listOf(
                MaterialShapes.SoftBoom,
                MaterialShapes.Sunny,
                MaterialShapes.Gem,
                MaterialShapes.Pill,
                MaterialShapes.Clover4Leaf
            )
        )
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    SplitBusTheme {
        LoadingScreen()
    }
}
