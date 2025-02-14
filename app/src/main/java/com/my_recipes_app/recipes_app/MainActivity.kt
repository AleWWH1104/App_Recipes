package com.my_recipes_app.recipes_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.my_recipes_app.recipes_app.navegacion.AppNavigation
import com.my_recipes_app.recipes_app.navegacion.NavigationState
import com.my_recipes_app.recipes_app.ui.account.viewmodel.UserViewModel
import com.my_recipes_app.recipes_app.ui.recipes.viewmodel.RecipeViewModel
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userRepository = (application as MyApp).userRepository
        val recipeRepository = (application as MyApp).recipeRepository
        val userViewModel: UserViewModel = UserViewModel(userRepository, application)
        val recipeViewModel: RecipeViewModel = RecipeViewModel(recipeRepository, application)

        userViewModel.checkSession()
        val user = userViewModel.getSession()
        setContent {
            Recipes_AppTheme {
                val navController = rememberNavController()

                val startDestination = if (user != null){
                    NavigationState.Home.route
                } else {
                    NavigationState.signIn.route
                }
                AppNavigation(navController, startDestination,userViewModel, recipeViewModel)
            }
        }
    }
}
