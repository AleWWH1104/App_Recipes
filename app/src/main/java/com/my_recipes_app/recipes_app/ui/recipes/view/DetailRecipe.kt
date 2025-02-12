package com.my_recipes_app.recipes_app.ui.recipes.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.ui.elements.topAppBar
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

@Composable
fun detailRecipeScreen(){
    Scaffold(
        topBar = { topAppBar()}
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
            Text(text = "Titulo receta", style = MaterialTheme.typography.titleMedium)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){


            }
            Column(modifier = Modifier.fillMaxWidth().padding(20.dp).weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                Text(text = stringResource(id = R.string.all_ingredients), style = MaterialTheme.typography.bodyLarge )
                Text(text = "Ingrediente 1", style = MaterialTheme.typography.bodySmall )
                Text(text = stringResource(id = R.string.preparation_recipe), style = MaterialTheme.typography.bodyLarge )
                Text(text = "Descripcion de como se prepara esta receta", style = MaterialTheme.typography.bodySmall )
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


@Preview
@Composable
fun prev3(){
    Recipes_AppTheme {
        detailRecipeScreen()
    }
}