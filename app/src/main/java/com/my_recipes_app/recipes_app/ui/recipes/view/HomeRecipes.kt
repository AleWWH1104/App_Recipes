package com.my_recipes_app.recipes_app.ui.recipes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.my_recipes_app.recipes_app.ui.elements.addRecipeButton
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.elements.recipeCard
import com.my_recipes_app.recipes_app.ui.recipes.viewmodel.RecipeViewModel

@Composable
fun homeRecipeScreen(navController: NavController, user: UserEntity, viewModel: RecipeViewModel){
    val recipes = viewModel.recipes.observeAsState(initial = emptyList())
    val recipeCount = viewModel.recipeCount.observeAsState(0)
    val recipesFavs= viewModel.recipesFav.observeAsState(emptyList())
    val recipesByTime = viewModel.recipesByTime.observeAsState(emptyList())
    val isLoading = viewModel.isLoading.observeAsState(initial = false)

    var selectedList by remember { mutableStateOf("ALL") }

    LaunchedEffect(Unit) {
        viewModel.fetchRecipes(user.userId)
        viewModel.fetchFavRecipes(user.userId)
        viewModel.fetchRecipesByTime(user.userId)
        viewModel.countRecipes(user.userId)
    }

    Scaffold (
        topBar = { topAppBar(navController, user)},
        floatingActionButton = { addRecipeButton(navController) },
        floatingActionButtonPosition = FabPosition.End
    ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFe3ddd4))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier
                    .background(Color(0xFF5e503f))
                    .height(200.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.banner_phrase),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(0.5f).padding(start = 20.dp)
                )
                Image(painter = painterResource(id= R.drawable.chef),
                    contentDescription = "chef",
                    modifier = Modifier.weight(0.5f).fillMaxHeight())
            }
            Spacer(modifier = Modifier.size(20.dp))
            Row (modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(painter = painterResource(id = R.drawable.marcador), contentDescription = "marcador", tint = Color(0xFF956934), modifier = Modifier.size(30.dp))
                Column {
                    Text(text = stringResource(id = R.string.saved_recipes), style = MaterialTheme.typography.bodySmall)
                    Text(text = "${recipeCount.value} recetas", style = MaterialTheme.typography.bodySmall)
                }
                Button(
                    onClick = { selectedList = "FAV" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF956934)
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null, tint = Color(0xFFe3ddd4))
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.favorites), style = MaterialTheme.typography.labelMedium)
                }
                Button(
                    onClick = { selectedList = "TIME" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF956934)
                    )
                ) {
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = null, tint = Color(0xFFe3ddd4)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(R.string.time), style = MaterialTheme.typography.labelMedium)
                }
            }
            if (isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else{
                val selectedRecipesList = when (selectedList){
                    "FAV" -> recipesFavs.value
                    "TIME" -> recipesByTime.value
                    else -> recipes.value.flatMap { it.recipes }
                }
                LazyColumn (
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ){ items(selectedRecipesList){recipe ->
                    recipeCard(recipe, navController)
                    }
                }
            }
        }
    }
}

