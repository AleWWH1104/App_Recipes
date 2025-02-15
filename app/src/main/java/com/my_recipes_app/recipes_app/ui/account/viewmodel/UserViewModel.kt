package com.my_recipes_app.recipes_app.ui.account.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.my_recipes_app.recipes_app.MainActivity
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.account.repository.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(private val repository: UsersRepository, application: Application): AndroidViewModel(application) {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _user = MutableLiveData<UserEntity?>()
    val user : LiveData<UserEntity?> = _user

    private val sharedPreferences = application.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    init {
        checkSession()
    }
    fun signIn(email: String, password: String){
        if (email.isBlank() || password.isBlank()){
            _errorMessage.value = "Los campos no pueden estar vacios"
            return
        }
        viewModelScope.launch {
            val userExist = repository.getUserFromEntity(email, password)
            if (userExist != null){
                _user.postValue(userExist)
                saveSession(userExist)
                _errorMessage.postValue(null)
            } else{
                _errorMessage.value = "Email o contraseña incorrectos"
            }
        }
    }

    fun signUp(username: String, email: String, password: String){
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return
        }
        viewModelScope.launch {
            try {
                val newUser = UserEntity(username = username, email = email, password = password)
                val userId = repository.insertUserInEntity(newUser).toInt()
                val registeredUser = UserEntity(userId, username, email, password)
                saveSession(registeredUser)
                _user.postValue(registeredUser)
                _errorMessage.postValue(null)
            } catch (e: Exception) {
                _errorMessage.postValue("Error al registrar usuario")
            }
        }
    }

    fun logOut(){
        viewModelScope.launch {
            clearSession()
            _user.postValue(null)
            delay(100)
            restartApp()
        }
    }

    fun deleteAccount(){
        viewModelScope.launch {
            _user.value?.let { user ->
                repository.deleteUserInEntity(user)
                clearSession()
                _user.postValue(null)
                delay(100)
                restartApp()
            }
        }
    }

    fun saveSession(user: UserEntity) {
        Log.d("UserViewModel", "Guardando sesión: userId=${user.userId}, username=${user.username}")
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
        Log.d("UserViewModel", "Recuperando sesión: userId=$userId, username=$username, email=$email")
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
        _user.postValue(currentUser)
    }

    private fun restartApp() {
        val intent = Intent(getApplication(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        getApplication<Application>().startActivity(intent)
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