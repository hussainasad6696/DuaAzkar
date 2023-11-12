package com.mera.islam.duaazkar.presentation.landing_screen.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mera.islam.duaazkar.R

enum class BottomNavItems(
    @DrawableRes val selectedIcon: Int,
    @DrawableRes val unSelectedIcon: Int,
    @StringRes val navName: Int
) {
    Home(
        R.drawable.ic_home_selected_icon,
        R.drawable.ic_home_unselected_icon,
        R.string.home
    ),
    Categories(
        R.drawable.ic_categories_selected_icon,
        R.drawable.ic_categories_unselected_icon,
        R.string.categories
    ),
    Bookmarks(
        R.drawable.ic_bookmark_selected_icon,
        R.drawable.ic_bookmark_unselected_icon,
        R.string.bookmarks
    )
}