package com.example.myapplication.content

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

data class NavigationBarItemData(val route: String, val icon: androidx.compose.ui.graphics.vector.ImageVector, val label: String)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen() {
    val navController = rememberNavController()
    val items = listOf(
        NavigationBarItemData("films", Icons.AutoMirrored.Filled.List, "Список фильмов"),
        NavigationBarItemData("profile", Icons.Rounded.AccountCircle, "Мой профиль"),
        NavigationBarItemData("favorites", Icons.Rounded.Favorite, "Мои фильмы")
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController, items) }
    ) { _ ->
        NavigationBar(navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, items: List<NavigationBarItemData>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = Color.DarkGray,
        modifier = Modifier.height(80.dp)
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) Color.Gray else Color.White,
                        modifier = Modifier.size(50.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) Color.Gray else Color.White
                    )
                },
                selected = isSelected,
                modifier = Modifier.padding(top = 5.dp),
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

        }
    }
}