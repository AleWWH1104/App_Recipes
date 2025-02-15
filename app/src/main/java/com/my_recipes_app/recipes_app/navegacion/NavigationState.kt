package com.my_recipes_app.recipes_app.navegacion

sealed class NavigationState(val route: String) {
    data object signIn:NavigationState("sigIn")
    data object signUp:NavigationState("sigUp")
    data object Home:NavigationState("home")
    data object Profile:NavigationState("profile")
    data object AddRecipe:NavigationState("addRecipe")
    data object RecipeDetail : NavigationState("recipe/{recipeId}") {
        fun createRoute(recipeId: Int): String {
            return "recipe/$recipeId"
        }
    }

}