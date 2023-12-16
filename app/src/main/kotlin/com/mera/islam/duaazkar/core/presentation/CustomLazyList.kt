package com.mera.islam.duaazkar.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.kaaveh.sdpcompose.sdp

@Composable
fun CustomLazyList(
    modifier: Modifier = Modifier,
    isLandscape: Boolean = false,
    columns: GridCells = GridCells.Fixed(1),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: LazyGridScope.() -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = if (isLandscape) GridCells.Fixed(2) else columns,
        verticalArrangement = Arrangement.spacedBy(10.sdp),
        horizontalArrangement = Arrangement.spacedBy(10.sdp),
        contentPadding = contentPadding
    ) { content() }
}