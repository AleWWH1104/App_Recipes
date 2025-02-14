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
import com.my_recipes_app.recipes_app.ui.recipes.view.addRecipeScreen
import com.my_recipes_app.recipes_app.ui.recipes.view.detailRecipeScreen
import com.my_recipes_app.recipes_app.ui.recipes.view.homeRecipeScreen
import com.my_recipes_app.recipes_app.ui.recipes.viewmodel.RecipeViewModel

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, userViewModel: UserViewModel, recipeViewModel: RecipeViewModel){
    val user = userViewModel.getSession()

    NavHost(navController= navController, startDestination = startDestination) {
        composable(NavigationState.signIn.route){
            signInScreen(navController, userViewModel)
        }
        composable(NavigationState.signUp.route){
            signUpScreen(navController, userViewModel)
        }
        composable(NavigationState.Home.route){
            if (user != null) {
                homeRecipeScreen(navController, user, recipeViewModel)
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
        composable(NavigationState.AddRecipe.route){
            if (user != null) {
                addRecipeScreen(navController, recipeViewModel, userViewModel)
            }
        }
        composable(route = NavigationState.RecipeDetail.route,
            arguments = listOf(
                navArgument("recipeName") {type = NavType.StringType},
                navArgument("time") {type = NavType.IntType},
                navArgument("isFav") {type = NavType.BoolType},
                navArgument("description") {type = NavType.StringType}
            )
        ){ backStackEntry ->
            val recipeName =backStackEntry.arguments?.getString("recipeName")?: ""
            val time =backStackEntry.arguments?.getInt("time")?: 0
            val isFav =backStackEntry.arguments?.getBoolean("isFav")?: false
            val description =backStackEntry.arguments?.getString("description")?: ""
            if (user != null) {
                detailRecipeScreen(navController, user, recipeName, time, isFav, description)
            }
        }
    }

}