package com.my_recipes_app.recipes_app.database.recipes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe (recipe: RecipeEntity)

    @Delete
    suspend fun deleteRecipe (recipe: RecipeEntity)

    @Query("SELECT * FROM recipesDB WHERE recipeId = :recipeId")
    fun getRecipeById(recipeId: Int): Flow<RecipeEntity>

    @Query("SELECT * FROM recipesDB WHERE userOwner = :userId")
    fun getRecipesByUser(userId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipesDB WHERE userOwner = :userId AND isFavorite = 1")
    fun getFavoriteRecipesByUser(userId: Int): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipesDB WHERE userOwner = :userId ORDER BY time ASC")
    fun getRecipesSortedByTime(userId: Int): Flow<List<RecipeEntity>>

    @Query("UPDATE recipesDB SET isFavorite = :isFavorite WHERE recipeId = :recipeId")
    suspend fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean)

    @Query("SELECT COUNT(*) FROM recipesDB WHERE userOwner = :userId")
    fun getRecipeCountByUser(userId: Int): Flow<Int>
}