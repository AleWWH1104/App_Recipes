package com.my_recipes_app.recipes_app.ui.account.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.account.repository.UsersRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UsersRepository, private val context: Context): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _user = MutableLiveData<UserEntity?>()
    val user : LiveData<UserEntity?> = _user

    private val _userWithRecipes = MutableLiveData<UsersWithRecipes>()
    val userWithRecipes: LiveData<UsersWithRecipes> get() = _userWithRecipes

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun signIn(email: String, password: String){
        if (email.isBlank() || password.isBlank()){
            _errorMessage.value = "Los campos no pueden estar vacios"
            return
        }
        viewModelScope.launch {
            val userExist = repository.getUserFromEntity(email, password)
            if (userExist != null){
                _user.value = userExist
                saveSession(userExist)
            } else{
                _errorMessage.value = "Email o contraseña incorrectos"
            }
        }
    }

    fun signUp(username: String, email: String, password: String){
        if (validateInputs(username, email, password)){
            viewModelScope.launch {
                val newUser = UserEntity(username = username, email = email, password = password)
                repository.insertUserInEntity(newUser)
                _user.value = newUser
                saveSession(newUser)
            }
        }
    }

    fun logOut(){
        clearSession()
        _user.value = null
    }

    fun deleteAccount(){
        viewModelScope.launch {
            _user.value?.let {
                repository.deleteUserInEntity(it)
                _user.value = null
            }
        }
    }

    fun saveSession(user: UserEntity) {
        sharedPreferences.edit()
            .putInt("userId", user.userId)
            .putString("username", user.username)
            .putString("email", user.email)
            .apply()
    }

    fun getSession(): UserEntity? {
        val userId = sharedPreferences.getInt("userId", -1)
        val username = sharedPreferences.getString("username", null)
        val email = sharedPreferences.getString("email", null)

        return if (userId != -1 && username != null && email != null) {
            UserEntity(userId, username, email, "")
        } else {
            null
        }
    }

    fun clearSession() {
        sharedPreferences.edit().clear().apply()
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean{
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return false
        }
        //Aqui se puede implementar restricciones para las contrasenas o para el email
        return true
    }

}