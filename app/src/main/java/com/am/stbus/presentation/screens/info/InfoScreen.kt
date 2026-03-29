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

package com.am.stbus.presentation.screens.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.am.stbus.R
import com.am.stbus.presentation.screens.common.AppBarScreen
import com.am.stbus.presentation.theme.AppTypography
import com.am.stbus.presentation.theme.SplitBusTheme

@Composable
fun InfoScreen(
    onGmapsClicked: () -> Unit,
    onPrometWebClicked: () -> Unit
) {
    AppBarScreen(
        title = stringResource(R.string.nav_information),
        titleColour = MaterialTheme.colorScheme.onBackground
    ) {
        InfoScreenRowItem(
            title = R.string.information_gmaps_title,
            description = R.string.information_gmaps_desc,
            showDivider = true
        ) {
            onGmapsClicked()
        }
        InfoScreenRowItem(
            title = R.string.information_promet_web,
            description = R.string.information_promet_desc,
            showDivider = false
        ) {
            onPrometWebClicked()
        }
    }
}

@Composable
fun InfoScreenRowItem(
    title: Int,
    description: Int,
    showDivider: Boolean,
    onClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClicked()
            }
            .padding(horizontal = 16.dp)

    ) {
        Text(
            modifier = Modifier.padding(top = 10.dp),
            style = AppTypography.titleMedium,
            text = stringResource(title)
        )
        Text(
            modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
            text = stringResource(description)
        )
        if (showDivider) {
            HorizontalDivider(thickness = 0.4.dp)
        }
    }
}


@Preview
@Composable
fun InfoScreenPreview() {
    SplitBusTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            InfoScreen(
                onGmapsClicked = {},
                onPrometWebClicked = {}
            )
        }
    }
}
