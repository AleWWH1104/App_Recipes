package com.my_recipes_app.recipes_app.database.recipes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDAO {
    @Transaction
    @Query("SELECT * FROM usersDB WHERE userId = :userId")
    fun getUserWithRecipes(userId: Int): List<UsersWithRecipes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe (recipe: RecipeEntity)

    @Query("DELETE FROM recipesDB WHERE recipeId = :recipeId")
    suspend fun deleteRecipe (recipeId: Int)

    @Query("SELECT * FROM recipesDB WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Int): RecipeEntity

    @Query("SELECT * FROM recipesDB WHERE userOwner = :userId")
    fun getRecipesByUser(userId: Int): List<RecipeEntity>

    @Query("SELECT * FROM recipesDB WHERE userOwner = :userId AND isFavorite = 1")
    fun getFavoriteRecipesByUser(userId: Int): List<RecipeEntity>

    @Query("SELECT * FROM recipesDB WHERE userOwner = :userId ORDER BY time ASC")
    fun getRecipesSortedByTime(userId: Int): List<RecipeEntity>

    @Query("UPDATE recipesDB SET isFavorite = :isFavorite WHERE recipeId = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM recipesDB WHERE userOwner = :userId")
    fun getRecipeCountByUser(userId: Int): Int

    @Query("UPDATE recipesDB SET image = :imageUrl WHERE recipeId = :recipeId")
    suspend fun updateRecipeImage(recipeId: Int, imageUrl: String)

}