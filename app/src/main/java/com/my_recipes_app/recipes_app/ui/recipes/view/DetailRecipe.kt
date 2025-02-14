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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

@Composable
fun detailRecipeScreen(navController: NavController, user: UserEntity,
                       recipeName: String, time: Int, isFav: Boolean, description: String){

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
            Text(text = recipeName, style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = stringResource(id = R.string.preparation_time) + "$time", style = MaterialTheme.typography.bodySmall)
                Icon(
                    imageVector = if (isFav) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "fav icon",
                    tint = Color(0xFF956934),
                    modifier = Modifier.size(70.dp)
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
                onClick = {},
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

