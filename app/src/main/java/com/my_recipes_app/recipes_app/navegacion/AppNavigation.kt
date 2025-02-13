package com.my_recipes_app.recipes_app.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.account.view.profileSideBar
import com.my_recipes_app.recipes_app.ui.account.view.signInScreen
import com.my_recipes_app.recipes_app.ui.account.view.signUpScreen
import com.my_recipes_app.recipes_app.ui.account.viewmodel.UserViewModel
import com.my_recipes_app.recipes_app.ui.recipes.view.homeRecipeScreen

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, userViewModel: UserViewModel){
    NavHost(navController= navController, startDestination = startDestination) {
        composable(NavigationState.signIn.route){
            signInScreen(navController, userViewModel)
        }
        composable(NavigationState.signUp.route){
            signUpScreen(navController, userViewModel)
        }
        composable(NavigationState.Home.route){
            val user = userViewModel.user.value
            if (user != null) {
                homeRecipeScreen(navController, user)
            }
        }
        composable(route = NavigationState.Profile.route,
            arguments = listOf(
                navArgument("username") {type = NavType.StringType},
                navArgument("email") {type = NavType.StringType}
            )
        ){ backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""

            profileSideBar(username = username, email= email, userViewModel, navController)
        }
    }

}