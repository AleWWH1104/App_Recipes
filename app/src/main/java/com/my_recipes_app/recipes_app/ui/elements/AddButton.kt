package com.my_recipes_app.recipes_app.ui.elements

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun addRecipeButton(){
    FloatingActionButton(
        onClick = {},
        shape = CircleShape,
        containerColor = Color(0xFF956934)) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "floating add button", tint = Color.White)
    }
}