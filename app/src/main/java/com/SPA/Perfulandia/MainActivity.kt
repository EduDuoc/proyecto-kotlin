package com.SPA.Perfulandia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.SPA.Perfulandia.data.database.DatabaseProvider
import com.SPA.Perfulandia.repository.ProductoRepository
import com.SPA.Perfulandia.ui.screens.*
import com.SPA.Perfulandia.ui.theme.PerfulandiaSPATheme
import com.SPA.Perfulandia.viewmodel.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerfulandiaSPATheme {

                val navController = rememberNavController()

                // Construcción del ViewModel con FACTORY (la misma instancia para todas las pantallas)
                val context = LocalContext.current
                val db = remember { DatabaseProvider.getDatabase(context) }
                val repository = remember { ProductoRepository(db.productoDao()) }

                val homeViewModel: HomeViewModel = viewModel(
                    factory = HomeViewModelFactory(repository)
                )

                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.HOME
                ) {

                    composable(NavRoutes.HOME) {
                        HomeScreen(
                            navController = navController,
                            viewModel = homeViewModel,
                            onNavigateAdd = {
                                navController.navigate(NavRoutes.ADD_PRODUCT)
                            }
                        )
                    }

                    composable(NavRoutes.ADD_PRODUCT) {
                        AddProductScreen(
                            homeViewModel = homeViewModel,
                            onBack = {
                                navController.popBackStack()
                            }
                        )
                    }

                    // Ruta de detalle con parámetro ID
                    composable(
                        route = "detail/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        DetailScreen(
                            navController = navController,
                            viewModel = homeViewModel,
                            productoId = id
                        )
                    }

                    // Ruta de edición con parámetro ID
                    composable(
                        route = "edit_product/{id}",
                        arguments = listOf(navArgument("id") {
                            type = NavType.IntType
                        })
                    ) { backStackEntry ->
                        val id = backStackEntry.arguments?.getInt("id")
                        EditProductScreen(
                            navController = navController,
                            homeViewModel = homeViewModel,
                            productoId = id
                        )
                    }
                }
            }
        }
    }
}
