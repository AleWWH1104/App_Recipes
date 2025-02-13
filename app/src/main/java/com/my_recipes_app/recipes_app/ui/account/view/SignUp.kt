package com.my_recipes_app.recipes_app.ui.account.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.my_recipes_app.recipes_app.R
import com.my_recipes_app.recipes_app.navegacion.NavigationState
import com.my_recipes_app.recipes_app.ui.account.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun signUpScreen(navController: NavController, viewModel: UserViewModel){
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidden by remember { mutableStateOf(true) }
    val user by viewModel.user.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    LaunchedEffect(user) {
        if (user != null) {
            navController.navigate(NavigationState.Home.route) {
                popUpTo(NavigationState.signUp.route) { inclusive = true }
            }
        }
        viewModel.clearError()
    }

    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFe3ddd4))
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f).padding(top = 50.dp).fillMaxWidth(0.9f)
            ) {
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.titleSmall
                )
                OutlinedTextField(
                    value = username,
                    onValueChange = {username = it},
                    label = { Text(text= stringResource( id =  R.string.username), style = MaterialTheme.typography.labelLarge) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                    )
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it},
                    label = { Text(text= stringResource( id =  R.string.email), style = MaterialTheme.typography.labelLarge) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                    )
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = {password = it},
                    label = { Text(text= stringResource( id =  R.string.password), style = MaterialTheme.typography.labelLarge) },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White, focusedBorderColor = Color(0xFF5e503f)
                    ),
                    visualTransformation =
                    if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = { hidden = !hidden }) {
                            val vector = painterResource(
                                if (hidden) R.drawable.eye_on
                                else R.drawable.eye_off
                            )
                            val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña"
                            Icon(painter = vector, contentDescription = description, tint = Color(0xFF5e503f), modifier = Modifier.size(20.dp))
                        }
                    }
                )
                TextButton(
                    onClick = {viewModel.signUp(username, email, password)},
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .background(Color(0xFF5e503f), shape = RoundedCornerShape(5.dp))
                ) {
                    Text(
                        text = stringResource(id= R.string.sign_up),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                errorMessage?.let { Text(it, color = Color.Red) }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(50.dp)
            ){
                Text(
                    text = stringResource(id = R.string.yes_member),
                    style = MaterialTheme.typography.bodySmall
                )
                TextButton(
                    onClick = {navController.navigate(NavigationState.signIn.route)}
                ) {
                    Text(
                        text = stringResource(id= R.string.sign_in),
                        style = MaterialTheme.typography.labelMedium.copy(textDecoration = TextDecoration.Underline, color = Color(0xFF5e503f))
                    )
                }
            }


        }
    }
}