package com.example.myapplication.content

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavigationBar(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "films")
    {
        composable("films") { FilmsScreen(navController)  }
        composable("filmDetail/{movieId}") {  x ->
            val movieId = x.arguments?.getString("movieId")?.toInt() ?: 0
            FilmDetailsScreen(navController, movieId)
        }
        composable("profile") { ProfileScreen() }
    }
}