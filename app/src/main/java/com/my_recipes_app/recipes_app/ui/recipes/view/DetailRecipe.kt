package com.my_recipes_app.recipes_app.ui.recipes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.navegacion.NavigationState
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.ui.recipes.viewmodel.RecipeViewModel

@Composable
fun detailRecipeScreen(navController: NavController, user: UserEntity, recipeId: Int, recipeName: String, userOwnerId: Int, time: Int,
                       isFav: Boolean, description: String, viewModel: RecipeViewModel) {

    Scaffold(
        topBar = { topAppBar(navController, user)}
    ) { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFe3ddd4)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(painter = painterResource(id = R.drawable.default_img), contentDescription = "img",
                modifier = Modifier.fillMaxWidth().height(200.dp), contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = recipeName ?: "", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.7f).background(Color(0xFFcec2b3)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = stringResource(id = R.string.preparation_time) + "$time", style = MaterialTheme.typography.labelLarge)
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "fav icon",
                    tint = Color(0xFF956934),
                    modifier = Modifier.size(40.dp)
                )
            }
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = stringResource(id = R.string.all_ingredients), style = MaterialTheme.typography.bodyLarge )
                Text(text = "Ingrediente 1", style = MaterialTheme.typography.bodySmall )
                Text(text = stringResource(id = R.string.preparation_recipe), style = MaterialTheme.typography.bodyLarge )
                Text(text = description, style = MaterialTheme.typography.bodySmall )
            }
            TextButton(
                onClick = {
                    viewModel.deleteRecipe(recipeId, userOwnerId, recipeName, time, isFav,
                        null.toString(), description)
                    navController.navigate(NavigationState.Home.route)
                },
                modifier = Modifier
                    .fillMaxWidth(0.5f).padding(bottom = 20.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF956934)),
                contentPadding = PaddingValues(vertical = 2.dp, horizontal = 12.dp)
            ){
                Text(
                    text = stringResource(id= R.string.delete_recipe),
                    style = MaterialTheme.typography.labelMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

