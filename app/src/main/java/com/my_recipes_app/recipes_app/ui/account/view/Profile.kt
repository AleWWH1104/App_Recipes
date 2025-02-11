package com.my_recipes_app.recipes_app.ui.account.view

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.ui.theme.Recipes_AppTheme

@Composable
fun profileSideBar(){
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color(0xFF5e503f))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null,
                modifier = Modifier.size(60.dp), tint = Color.Gray)
            Text(text = stringResource(id = R.string.username), style = MaterialTheme.typography.bodyMedium)
        }
        Column (
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {}
            ) {
                Icon(
                    imageVector = Icons.Filled.ExitToApp,
                    contentDescription = "log out",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = stringResource(id= R.string.log_out), style = MaterialTheme.typography.labelMedium)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {}
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "delete account",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Text(text = stringResource(id= R.string.delete_account), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

