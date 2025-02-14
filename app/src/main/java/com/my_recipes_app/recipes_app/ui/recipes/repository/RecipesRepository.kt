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

    suspend fun insertRecipeInEntity(recipe: RecipeEntity){
        return recipesDAO.insertRecipe(recipe)
    }

    suspend fun deleteRecipeInEntity(recipe: RecipeEntity){
        return recipesDAO.deleteRecipe(recipe)
    }

    fun getFavoriteRecipesByUser(userId: Int): List<RecipeEntity>{
        return recipesDAO.getFavoriteRecipesByUser(userId)
    }

    fun getRecipesByTime(userId: Int): List<RecipeEntity>{
        return recipesDAO.getRecipesSortedByTime(userId)
    }

    fun getRecipeById(recipeId: Int): RecipeEntity{
        return recipesDAO.getRecipeById(recipeId)
    }

    suspend fun getFavStatus(recipeId: Int, isFavorite: Boolean){
        return recipesDAO.updateFavoriteStatus(recipeId, isFavorite)
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