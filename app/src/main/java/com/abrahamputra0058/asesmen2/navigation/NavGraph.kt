package com.abrahamputra0058.asesmen2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.abrahamputra0058.asesmen2.ui.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route){
            MainScreen(navController)
        }
//        composable(route = Screen.FormAdd.route){
//            DetailAgendaScreen(navController)
//        }
//        composable(route = Screen.About.route){
//            AboutScreen(navController)
//        }
    }
}