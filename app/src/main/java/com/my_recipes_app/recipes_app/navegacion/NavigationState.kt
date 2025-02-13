package com.my_recipes_app.recipes_app.navegacion

sealed class NavigationState(val route: String) {
    data object signIn:NavigationState("sigIn")
    data object signUp:NavigationState("sigUp")
    data object Home:NavigationState("home")
    data object Profile:NavigationState("profile/{username}/{email}"){
        fun createRoute(username: String, email: String): String{
            return "profile/$username/$email"
        }
    }
}