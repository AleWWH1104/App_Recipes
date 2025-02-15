package com.my_recipes_app.recipes_app.ui.account.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.database.users.UsersDAO
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDAO: UsersDAO) {

    suspend fun getUserFromEntity(email: String, password: String): UserEntity{
        return usersDAO.getUser(email, password)
    }

    suspend fun insertUserInEntity(user: UserEntity): Long{
        return usersDAO.insertUser(user)
    }

    suspend fun deleteUserInEntity(user: UserEntity){
        usersDAO.deleteUser(user)
    }

    suspend fun insertDefaultUser() {
        val existingUser = usersDAO.getUser("info@koalit.dev", "koalit123")
        if (existingUser == null) {
            val defaultUser = UserEntity(
                username = "Koalit",
                email = "info@koalit.dev",
                password = "koalit123"
            )
            usersDAO.insertUser(defaultUser)
            Log.d("UsersRepository", "Usuario por defecto insertado")
        } else {
            Log.d("UsersRepository", "El usuario por defecto ya existe")
        }
    }
}