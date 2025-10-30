package com.team3.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.team3.compose.ui.details.DetailsScreen
import com.team3.compose.ui.home.HomeScreen

@Composable
fun ComposeApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.HOME
    ) {
        composable(Route.HOME) {
            HomeScreen(
                onStockClick = { stockCode ->
                    navController.navigate("${Route.DETAIL}/$stockCode")
                }
            )
        }
        composable(
            route = "${Route.DETAIL}/{${Argument.STOCK_CODE}}",
            arguments = listOf(navArgument(Argument.STOCK_CODE) { type = NavType.StringType })
        ) {
            DetailsScreen(navController = navController)
        }
    }
}

object Route {
    const val HOME = "home"
    const val DETAIL = "detail"
}

object Argument {
    const val STOCK_CODE = "stockCode"
}