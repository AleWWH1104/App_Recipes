package com.my_recipes_app.recipes_app.database.ingredients

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: IngredientEntity)

    @Query("SELECT * FROM ingredientsDB WHERE recipeOwner = :recipeId")
    fun getIngredientsForRecipe(recipeId: Int): Flow<List<IngredientEntity>>

    @Delete
    suspend fun deleteIngredient(ingredient: IngredientEntity)
}
