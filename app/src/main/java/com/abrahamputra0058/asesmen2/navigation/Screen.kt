package com.abrahamputra0058.asesmen2.navigation

import com.abrahamputra0058.asesmen2.ui.screen.KEY_ID_AGENDA

sealed class Screen(val route: String) {
    data object Home: Screen("mainScreen")
    data object FormAdd: Screen("detailScreen")
    data object About: Screen("aboutScreen")
    data object FormEdit: Screen("detailScreen/{$KEY_ID_AGENDA}"){
        fun withId(idAgenda: Long) = "detailScreen/$idAgenda"
    }
    data object RecycleBin: Screen("recycleBinScreen")
}