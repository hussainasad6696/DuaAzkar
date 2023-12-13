package com.mera.islam.duaazkar.presentation.asma_ul_husna_screen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mera.islam.duaazkar.R
import com.mera.islam.duaazkar.core.presentation.CustomLazyList
import com.mera.islam.duaazkar.core.presentation.DefaultTopAppBar
import com.mera.islam.duaazkar.core.presentation.DuaAzkarWithBackground
import com.mera.islam.duaazkar.core.presentation.Loading
import com.mera.islam.duaazkar.core.utils.EventResources
import com.mera.islam.duaazkar.domain.models.asmaUlHusna.AsmaulHusnaModel
import com.mera.islam.duaazkar.presentation.asma_ul_husna_screen.components.AsmaListItem
import ir.kaaveh.sdpcompose.sdp

@Composable
fun AsmaulHusnaScreen(
    navController: NavHostController,
    viewModel: AsmaulHusnaScreenViewModel = hiltViewModel()
) {
    DuaAzkarWithBackground(modifier = Modifier.fillMaxSize(), addScaffolding = true, topBar = {
        DefaultTopAppBar(
            title = stringResource(id = R.string.asma_ul_husna),
            navHostController = navController,
            hasSearch = false
        )
    }) {
        val asmaulHusna by viewModel.asma.collectAsStateWithLifecycle()

        when (asmaulHusna) {
            EventResources.Loading -> Loading(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            )

            is EventResources.Success -> {
                val list = (asmaulHusna as EventResources.Success<List<AsmaulHusnaModel>>).template

                CustomLazyList(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    itemsIndexed(list) { index, item ->
                        AsmaListItem(
                            index = index + 1,
                            title = item.transliteration,
                            subTitle = item.enMeaning
                        ) {

                        }

                        if (index == list.lastIndex)
                            Spacer(modifier = Modifier.height(15.sdp))
                    }
                }
            }
        }
    }
}