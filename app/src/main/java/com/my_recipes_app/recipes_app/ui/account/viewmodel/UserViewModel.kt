package com.my_recipes_app.recipes_app.ui.account.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.account.repository.UsersRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UsersRepository, application: Application): AndroidViewModel(application) {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _user = MutableLiveData<UserEntity?>()
    val user : LiveData<UserEntity?> = _user

    private val _userWithRecipes = MutableLiveData<UsersWithRecipes>()
    val userWithRecipes: LiveData<UsersWithRecipes> get() = _userWithRecipes

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

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
                _errorMessage.value = null
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
                _errorMessage.value = null
            }
        }
    }

    fun logOut(){
        clearSession()
        _user.value = null
    }

    fun deleteAccount(){
        viewModelScope.launch {
            _user.value?.let { user ->
                repository.deleteUserInEntity(user)
                val userCheck = repository.getUserFromEntity(user.email, user.password)
                if (userCheck == null) {
                    clearSession()
                    _user.postValue(null)
                } else {
                    Log.e("DeleteAccount", "El usuario aún existe en la base de datos")
                }
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

    fun checkSession(){
        val currentUser = getSession()  // Recupera la sesión guardada
        if (currentUser != null) {
            _user.value = currentUser  // Si la sesión está activa, actualiza el usuario en el ViewModel
        }
    }

    private fun validateInputs(username: String, email: String, password: String): Boolean{
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return false
        }
        //Aqui se puede implementar restricciones para las contrasenas o para el email
        return true
    }

    fun clearError() {
        _errorMessage.value = null
    }


}