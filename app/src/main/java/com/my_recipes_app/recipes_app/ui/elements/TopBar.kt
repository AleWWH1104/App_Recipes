package com.my_recipes_app.recipes_app.ui.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun topAppBar(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF5e503f))
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = {}) {
            Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu", modifier = Modifier.size(50.dp))
        }
    }
}