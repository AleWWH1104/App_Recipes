package com.my_recipes_app.recipes_app.ui.recipes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.ui.elements.addIngredientField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addRecipeScreen(){
    var checked by remember { mutableStateOf(false) }
    Scaffold (
        topBar = { topAppBar() },
    ){paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Color(0xFFe3ddd4)),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(painter = painterResource(id = R.drawable.default_img), contentDescription = "img",
                modifier = Modifier.fillMaxWidth().height(150.dp), contentScale = ContentScale.Crop)
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(modifier = Modifier.weight(0.8f), verticalArrangement = Arrangement.spacedBy(8.dp)){
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text= stringResource( id =  R.string.name_recipe), style = MaterialTheme.typography.labelLarge) },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
                        Text(text = stringResource(id= R.string.preparation_time), style = MaterialTheme.typography.bodySmall)
                        OutlinedTextField(
                            value = "",
                            onValueChange = {},
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                            ),
                            modifier = Modifier.width(60.dp).height(40.dp)
                        )
                    }
                }
                IconToggleButton(checked = checked, onCheckedChange = { checked = it}, modifier = Modifier.weight(0.2f)) {
                    Icon(
                        imageVector = if (checked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "fav icon",
                        tint = if (checked) Color(0xFF956934) else Color(0xFF956934),
                        modifier = Modifier.size(70.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.background(Color(0xFFcec2b3)).fillMaxWidth().height(5.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = stringResource(id = R.string.add_ingredients), style = MaterialTheme.typography.titleSmall)
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.fillMaxWidth().weight(1f)) { items(2){
                addIngredientField()
                }
            }
            Spacer(modifier = Modifier.background(Color(0xFFcec2b3)).fillMaxWidth().height(5.dp))
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = stringResource(id = R.string.preparation_recipe), style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text= stringResource( id =  R.string.all_ingredients), style = MaterialTheme.typography.labelLarge) },
                maxLines = 4,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                ),
                modifier = Modifier.fillMaxWidth(0.7f).height(100.dp).padding(bottom = 16.dp)
            )
        }

    }
}


@Preview
@Composable
fun prev2(){
    Recipes_AppTheme {
        addRecipeScreen()
    }
}