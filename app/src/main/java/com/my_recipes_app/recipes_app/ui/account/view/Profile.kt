package com.my_recipes_app.recipes_app.ui.account.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.navegacion.NavigationState
import com.my_recipes_app.recipes_app.ui.account.viewmodel.UserViewModel

@Composable
fun profileSideBar(viewModel: UserViewModel, navController: NavController){
    val user by viewModel.user.observeAsState()
    val username = user?.username ?: "Invitado"

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(Color(0xFF5e503f))
            .padding(16.dp)
            .clickable { navController.popBackStack() },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row (modifier = Modifier.padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = null,
                modifier = Modifier.size(60.dp), tint = Color.Gray)
            Text(text = username, style = MaterialTheme.typography.bodyMedium)
        }
        Column (
            modifier = Modifier.fillMaxWidth().padding(bottom=50.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clickable {
                        viewModel.logOut()
                        navController.navigate(NavigationState.signIn.route){
                            popUpTo(0)
                        }
                    }
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
                    .clickable {
                        viewModel.deleteAccount()
                        navController.navigate(NavigationState.signUp.route){
                            popUpTo(0)
                        }
                    }
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

