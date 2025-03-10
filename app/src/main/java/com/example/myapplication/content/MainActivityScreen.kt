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
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainActivityScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.DarkGray,
                modifier = Modifier.height(100.dp)
            ) {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.List,
                            contentDescription = "Films",
                            tint = Color.White,
                            modifier = Modifier
                                .size(60.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Список фильмов",
                            color = Color.White,
                        )

                    },
                    modifier = Modifier.padding(top = 10.dp),
                    selected = false,
                    onClick = {
                        navController.navigate("films")
                    }


                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            Icons.Rounded.AccountCircle,
                            contentDescription = "Profile",
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    },
                    label = {
                        Text(
                            text = "Мой Профиль",
                            color = Color.White,
                        )

                    },
                    selected = false,
                    modifier = Modifier.padding(top = 10.dp),
                    onClick = {
                        navController.navigate("profile")
                    }
                )
            }
        }
    ) { _ ->
        NavigationBar(navController)
    }
}

