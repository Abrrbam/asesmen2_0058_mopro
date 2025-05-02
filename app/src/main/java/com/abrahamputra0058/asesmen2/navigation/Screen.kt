package com.abrahamputra0058.asesmen2.navigation

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormAdd: Screen("detailScreen")
    data object About: Screen("aboutScreen")
}