package com.my_recipes_app.recipes_app.ui.recipes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.my_recipes_app.recipes_app.ui.elements.addRecipeButton
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

@Composable
fun homeRecipeScreen(){
    Scaffold (
        topBar = { topAppBar()},
        floatingActionButton = { addRecipeButton() },
        floatingActionButtonPosition = FabPosition.End
    ){paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFe3ddd4))
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ){}
    }
}


@Preview
@Composable
fun prev(){
    Recipes_AppTheme {
        homeRecipeScreen()
    }
}