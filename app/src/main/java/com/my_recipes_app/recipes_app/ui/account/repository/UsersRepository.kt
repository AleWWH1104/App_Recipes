package com.my_recipes_app.recipes_app.ui.account.repository

import android.content.Context
import android.content.SharedPreferences
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.database.users.UsersDAO
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val usersDAO: UsersDAO) {

    suspend fun getUserFromEntity(email: String, password: String): UserEntity?{
        return usersDAO.getUser(email, password)
    }

    fun getUsersRecipes(userId: Int): Flow<UsersWithRecipes>{
        return usersDAO.getUserWithRecipes(userId)
    }

    suspend fun insertUserInEntity(user: UserEntity){
        return usersDAO.insertUser(user)
    }

    suspend fun deleteUserInEntity(user: UserEntity){
        usersDAO.deleteUser(user)
    }


}