package com.example.pruebatotalplayapp.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pruebatotalplayapp.presentation.details.PlaceDetailsScreen
import com.example.pruebatotalplayapp.presentation.login.LoginScreen
import com.example.pruebatotalplayapp.presentation.search.SearchScreen


sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Search : Screen("search")
    object PlaceDetails : Screen("place_details/{placeId}") {
        fun createRoute(placeId: String) = "place_details/$placeId"
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Search.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onPlaceClick = { placeId ->
                    navController.navigate(Screen.PlaceDetails.createRoute(placeId))
                }
            )
        }

        composable(
            route = Screen.PlaceDetails.route,
            arguments = listOf(
                navArgument("placeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId") ?: return@composable
            PlaceDetailsScreen(
                placeId = placeId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}