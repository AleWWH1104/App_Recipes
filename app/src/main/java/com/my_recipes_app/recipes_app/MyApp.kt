package com.my_recipes_app.recipes_app

import android.app.Application
import androidx.room.Room
import com.my_recipes_app.recipes_app.database.AppDataBase

class MyApp: Application() {
    // Singleton instance of the Room database
    private lateinit var database: AppDataBase
        private set

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext, AppDataBase::class.java, "recipesAppDatabase").build()
    }
}