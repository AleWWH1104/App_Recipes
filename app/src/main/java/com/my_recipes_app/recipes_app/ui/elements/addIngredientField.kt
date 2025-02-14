package com.my_recipes_app.recipes_app.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.ui.recipes.view.addRecipeScreen
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addIngredientField(){
    Column (modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            OutlinedTextField(
                value = "",
                onValueChange = {},
                label = { Text(text= stringResource( id =  R.string.all_ingredients), style = MaterialTheme.typography.labelLarge) },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                ),
                textStyle = MaterialTheme.typography.labelLarge,
                modifier = Modifier.fillMaxWidth(0.7f).height(40.dp)
            )
            Row(
                modifier = Modifier.padding(bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                IconButton(onClick = {}) {
                    Icon(painter = painterResource(R.drawable.minus), contentDescription = "minus", tint = Color(0xFF5e503f), modifier = Modifier.size(30.dp))
                }
                Text(text = "10", style = MaterialTheme.typography.titleSmall)
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Filled.AddCircle, contentDescription = "add", tint = Color(0xFF5e503f), modifier = Modifier.size(30.dp))
                }
            }
        }
        TextButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF956934)),
            contentPadding = PaddingValues(vertical = 2.dp, horizontal = 12.dp)
        ){
            Text(
                text = stringResource(id= R.string.add),
                style = MaterialTheme.typography.labelMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview
@Composable
fun prev3(){
    Recipes_AppTheme {
        addIngredientField()
    }
}