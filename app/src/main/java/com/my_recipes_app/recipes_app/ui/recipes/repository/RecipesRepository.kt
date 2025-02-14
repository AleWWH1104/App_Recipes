package com.my_recipes_app.recipes_app.ui.recipes.repository

import com.my_recipes_app.recipes_app.database.ingredients.IngredientDAO
import com.my_recipes_app.recipes_app.database.ingredients.IngredientEntity
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.database.recipes.RecipesDAO
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import kotlinx.coroutines.flow.Flow

class RecipesRepository(private val recipesDAO: RecipesDAO, private val ingredientDAO: IngredientDAO) {
    fun getUsersRecipes(userId: Int): List<UsersWithRecipes>{
        return recipesDAO.getUserWithRecipes(userId)
    }

    fun getRecipeDetail(recipeId: Int): RecipeEntity{
        return recipesDAO.getRecipeById(recipeId)
    }

    suspend fun insertRecipeInEntity(recipe: RecipeEntity){
        return recipesDAO.insertRecipe(recipe)
    }

    suspend fun deleteRecipeInEntity(recipeId: Int){
        return recipesDAO.deleteRecipe(recipeId)
    }

    fun getFavoriteRecipesByUser(userId: Int): List<RecipeEntity>{
        return recipesDAO.getFavoriteRecipesByUser(userId)
    }

    fun getRecipesByTime(userId: Int): List<RecipeEntity>{
        return recipesDAO.getRecipesSortedByTime(userId)
    }

    suspend fun getFavStatus(recipeId: Int, isFavorite: Boolean){
        return recipesDAO.updateFavoriteStatus(recipeId, isFavorite)
    }

    suspend fun updateRecipeImage(recipeId: Int, imageUrl: String) {
        return recipesDAO.updateRecipeImage(recipeId, imageUrl)
    }

    fun getRecipeCountByUser(userId: Int): Int{
        return recipesDAO.getRecipeCountByUser(userId)
    }

    fun getIngredientsForRecipe(recipeId: Int): List<IngredientEntity>{
        return ingredientDAO.getIngredientsForRecipe(recipeId)
    }

    suspend fun insertIngredientInEntity(ingredient: IngredientEntity){
        return ingredientDAO.insertIngredient(ingredient)
    }

}