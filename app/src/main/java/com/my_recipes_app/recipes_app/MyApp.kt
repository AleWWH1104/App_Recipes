package com.my_recipes_app.recipes_app

import android.app.Application
import androidx.room.Room
import com.my_recipes_app.recipes_app.database.AppDataBase
import com.my_recipes_app.recipes_app.ui.account.repository.UsersRepository
import com.my_recipes_app.recipes_app.ui.recipes.repository.RecipesRepository

class MyApp: Application() {
    // Singleton instance of the Room database
    private lateinit var database: AppDataBase
        private set

    lateinit var userRepository: UsersRepository
        private set

    lateinit var recipeRepository: RecipesRepository
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext, AppDataBase::class.java, "recipesAppDatabase").build()

        userRepository= UsersRepository(database.getUserDao())
        recipeRepository= RecipesRepository(database.getRecipeDao())
    }
}