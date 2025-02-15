package com.my_recipes_app.recipes_app.database

import androidx.room.*
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.database.recipes.RecipesDAO
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.database.users.UsersDAO

@Database(entities = [UserEntity::class, RecipeEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun getUserDao():UsersDAO
    abstract fun getRecipeDao(): RecipesDAO
}