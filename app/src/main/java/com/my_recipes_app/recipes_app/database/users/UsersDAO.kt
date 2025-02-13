package com.my_recipes_app.recipes_app.database.users

import androidx.room.*
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import kotlinx.coroutines.flow.Flow


@Dao
interface UsersDAO {
    @Transaction
    @Query("SELECT * FROM usersDB WHERE userId = :userId")
    fun getUserWithRecipes(userId: Int): Flow<UsersWithRecipes>

    @Query("SELECT * FROM usersDB WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

}