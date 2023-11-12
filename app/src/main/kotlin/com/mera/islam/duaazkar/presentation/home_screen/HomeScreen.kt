package com.mera.islam.duaazkar.presentation.home_screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.presentation.landing_screen.components.LandingScreenTopSelection
import ir.kaaveh.sdpcompose.sdp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(10.sdp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.sdp)
                .horizontalScroll(rememberScrollState())
        ) {
            LandingScreenTopSelection(
                modifier = Modifier.padding(end = 5.sdp),
                resource = R.drawable.ic_morning_azkar
            )

            LandingScreenTopSelection(
                modifier = Modifier.padding(end = 5.sdp),
                resource = R.drawable.ic_evening_azkar
            )

        }
    }
}