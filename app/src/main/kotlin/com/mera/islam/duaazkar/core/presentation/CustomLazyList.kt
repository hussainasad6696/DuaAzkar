package com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CustomLazyList(modifier: Modifier = Modifier,content: LazyGridScope.() -> Unit) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(1),
        verticalArrangement = Arrangement.spacedBy(10.sdp),
        horizontalArrangement = Arrangement.spacedBy(10.sdp)
    ) { content() }
}