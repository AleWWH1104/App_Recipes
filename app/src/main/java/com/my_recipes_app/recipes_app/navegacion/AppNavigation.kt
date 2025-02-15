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
        composable(route = NavigationState.Profile.route){
            profileSideBar(userViewModel, navController)
        }
        composable(NavigationState.AddRecipe.route){
            if (user != null) {
                addRecipeScreen(navController, recipeViewModel, userViewModel)
            }
        }
        composable(route = NavigationState.RecipeDetail.route,
            arguments = listOf(
                navArgument("recipeId") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments?.getInt("recipeId") ?: 0

            if (user != null) {
                detailRecipeScreen(navController, user, recipeId, recipeViewModel)
            }
        }

    }

}