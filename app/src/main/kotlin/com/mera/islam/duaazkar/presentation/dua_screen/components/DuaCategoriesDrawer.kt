package com.mera.islam.duaazkar.presentation.dua_screen.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.models.dua.DuaType
import com.mera.islam.duaazkar.presentation.dua_screen.DuaScreenViewModel
import com.mera.islam.duaazkar.presentation.home_screen.components.DuaTypesWithCountView
import ir.kaaveh.sdpcompose.sdp

@Composable
fun DuaCategoriesDrawer(
    viewModel: DuaScreenViewModel,
    onCloseDrawerClick: () -> Unit = {},
    onCategorySelected: (DuaType) -> Unit = {}
) {
    ModalDrawerSheet(
        drawerContainerColor = Color.White,
        drawerShape = RoundedCornerShape(0)
    ) {
        DuaSettingsNavigationTitle(title = stringResource(id = R.string.categories), onClose = onCloseDrawerClick)

        val duaCategories by viewModel.duaTypeWithCount.collectAsStateWithLifecycle()

        when (duaCategories) {
            EventResources.Loading -> Loading(modifier = Modifier.fillMaxSize())
            is EventResources.Success -> {
                val list = (duaCategories as EventResources.Success).template

                CustomLazyList(
                    Modifier
                        .padding(horizontal = 10.sdp)
                        .padding(top = 5.sdp),
                    isLandscape = false,
                    content = {
                        items(list.size) { index ->
                            val dua = list[index]

                            DuaTypesWithCountView(
                                duaType = dua.getDuaType(),
                                noOfDua = dua.count,
                                addBackground = false,
                                onNextClick = {
                                    onCategorySelected(DuaType.toDuaType(dua.duaType))
                                })

                            if (index == list.lastIndex)
                                Spacer(modifier = Modifier.height(15.sdp))
                        }
                    })
            }
        }
    }
}