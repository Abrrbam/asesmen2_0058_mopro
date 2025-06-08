package com.abrahamputra0058.asesmen2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abrahamputra0058.asesmen2.ui.screen.AboutScreen
import com.abrahamputra0058.asesmen2.ui.screen.DetailAgendaScreen
import com.abrahamputra0058.asesmen2.ui.screen.KEY_ID_AGENDA
import com.abrahamputra0058.asesmen2.ui.screen.MainScreen
import com.abrahamputra0058.asesmen2.ui.screen.RecycleBinScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
        composable(route = Screen.FormAdd.route){
            DetailAgendaScreen(navController)
        }
        composable(route = Screen.About.route){
            AboutScreen(navController)
        }
        composable(
            route = Screen.FormEdit.route,
            arguments = listOf(
                navArgument(KEY_ID_AGENDA) { type = NavType.LongType }
            )
        ){ navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getLong(KEY_ID_AGENDA)
            DetailAgendaScreen(navController, id)
        }
        composable(route = Screen.RecycleBin.route){
            RecycleBinScreen(navController)
        }


    }
}