package com.SPA.Perfulandia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.SPA.Perfulandia.ui.screens.DetailScreen
import com.SPA.Perfulandia.ui.screens.HomeScreen
import com.SPA.Perfulandia.ui.screens.ProfileScreen
import com.SPA.Perfulandia.ui.theme.PerfulandiaSPATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerfulandiaSPATheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.HOME
                ) {
                    composable(NavRoutes.HOME) {
                        HomeScreen(
                            onGoToDetail = { id -> navController.navigate(NavRoutes.detailWithId(id)) },
                            onGoToProfile = { navController.navigate(NavRoutes.PROFILE) }
                        )
                    }
                    composable(
                        route = NavRoutes.DETAIL_WITH_ARG,
                        arguments = listOf(navArgument("id") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id") ?: 1
                        DetailScreen(
                            id = id,
                            onBackToHome = { navController.popBackStack(NavRoutes.HOME, inclusive = false) }
                        )
                    }
                    composable(NavRoutes.PROFILE) {
                        ProfileScreen(
                            onBackToHome = { navController.popBackStack(NavRoutes.HOME, inclusive = false) }
                        )
                    }
                }
            }
        }
    }
}
