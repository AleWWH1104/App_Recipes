package com.my_recipes_app.recipes_app.ui.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.navegacion.NavigationState
import com.my_recipes_app.recipes_app.ui.recipes.view.homeRecipeScreen
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

@Composable
fun recipeCard(recipe: RecipeEntity, navController: NavController){
    Card (
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 16.dp)
            .clickable { navController.navigate(NavigationState.RecipeDetail.createRoute(
                recipe.recipeId, recipe.recipeName, recipe.userOwnerId, recipe.time, recipe.isFavorite, recipe.description))},
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id= R.drawable.default_img), contentDescription = "recipe img",
                contentScale = ContentScale.Crop, modifier = Modifier.weight(0.3f))
            Column( verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(0.6f).padding(start = 10.dp)) {
                Text(text = recipe.recipeName, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${recipe.time} min", style = MaterialTheme.typography.bodySmall)
            }
            if (recipe.isFavorite){
                Icon(imageVector = Icons.Filled.Favorite, contentDescription= "fav", tint= Color(0xFF5e503f), modifier = Modifier.size(30.dp).weight(0.1f))
            }else{
                Spacer(modifier = Modifier.size(30.dp).weight(0.1f))
            }
        }
    }
}
