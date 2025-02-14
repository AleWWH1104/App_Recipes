package com.my_recipes_app.recipes_app.ui.recipes.view

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.navegacion.NavigationState
import com.my_recipes_app.recipes_app.ui.elements.imageField
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.ui.recipes.viewmodel.RecipeViewModel

@Composable
fun detailRecipeScreen(navController: NavController, user: UserEntity, recipeId: Int, viewModel: RecipeViewModel) {
    val recipe by viewModel.recipeDetail.observeAsState()
    var isFavoriteState by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf("") }

    LaunchedEffect(recipeId) {
        viewModel.fetchRecipeDetail(recipeId)
    }

    if (recipe == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    LaunchedEffect(recipe) {
        recipe?.let {
            isFavoriteState = it.isFavorite
            Log.d("DetailScreen", "Image updated: ${it.image}")
            imageUrl = it.image ?: ""
        }
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUrl = uri.toString()
            viewModel.updateRecipeImage(recipeId, imageUrl)
        }
    }
    Scaffold(
        topBar = { topAppBar(navController, user)}
    ) { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFe3ddd4)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { launcher.launch("image/*") },  // Permite cambiar la imagen
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = if (imageUrl.isEmpty()) {
                        painterResource(id = R.drawable.default_img)
                    } else {
                        rememberAsyncImagePainter(imageUrl)
                    },
                    contentDescription = "Recipe Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = recipe?.recipeName ?: "", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.size(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.7f).background(Color(0xFFcec2b3)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(text = stringResource(id = R.string.preparation_time) + "${recipe?.time ?: 0}", style = MaterialTheme.typography.labelLarge)
                Icon(
                    imageVector = if (isFavoriteState) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "fav icon",
                    tint = Color(0xFF956934),
                    modifier = Modifier.size(40.dp).clickable {  isFavoriteState = !isFavoriteState
                        viewModel.updateFavoriteStatus(recipeId, isFavoriteState) }
                )
            }
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = stringResource(id = R.string.all_ingredients), style = MaterialTheme.typography.bodyLarge )
                Text(text = "Ingrediente 1", style = MaterialTheme.typography.bodySmall )
                Text(text = stringResource(id = R.string.preparation_recipe), style = MaterialTheme.typography.bodyLarge )
                Text(text = recipe?.description ?: "", style = MaterialTheme.typography.bodySmall )
            }
            TextButton(
                onClick = {
                    recipe?.let {
                        viewModel.deleteRecipe(it.recipeId, it.userOwnerId)
                        navController.navigate(NavigationState.Home.route)
                    }
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

