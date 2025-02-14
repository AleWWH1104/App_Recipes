package com.my_recipes_app.recipes_app.ui.recipes.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.my_recipes_app.recipes_app.database.ingredients.IngredientEntity
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import com.my_recipes_app.recipes_app.database.users.UserEntity
import com.my_recipes_app.recipes_app.ui.recipes.repository.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipesRepository, application: Application) : AndroidViewModel(application) {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _recipes = MutableLiveData<List<UsersWithRecipes>>()
    val recipes: LiveData<List<UsersWithRecipes>> = _recipes

    private val _recipesFav = MutableLiveData<List<RecipeEntity>>()
    val recipesFav: LiveData<List<RecipeEntity>> = _recipesFav

    private val _recipesByTime = MutableLiveData<List<RecipeEntity>>()
    val recipesByTime: LiveData<List<RecipeEntity>> = _recipesByTime

    private val _ingredients = MutableLiveData<List<IngredientEntity>>()
    val ingredients: LiveData<List<IngredientEntity>> = _ingredients

    private val _recipeCount = MutableLiveData<Int>()
    val recipeCount: LiveData<Int> = _recipeCount

    private val _recipeDetail = MutableLiveData<RecipeEntity>()
    val mealDetail: LiveData<RecipeEntity> = _recipeDetail

    private val _isRecipeAdded = MutableLiveData<Boolean>()
    val isRecipeAdded: LiveData<Boolean> = _isRecipeAdded

    fun fetchRecipes(userId: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = repository.getUsersRecipes(userId)
                _recipes.postValue(recipes)
                Log.d("RecipeViewModel", "Recipes loaded")
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun insertRecipe(userId: Int, name: String, time: String, isFavorite: Boolean, description: String) {
        if (validateInputs(name, description, time)) {
            viewModelScope.launch {
                val newRecipe = RecipeEntity(
                    recipeId = 0,
                    userOwnerId = userId,
                    recipeName = name,
                    time = time.toIntOrNull() ?: 0,
                    isFavorite = isFavorite,
                    description = description
                )
                repository.insertRecipeInEntity(newRecipe)
                _isRecipeAdded.value = true
                fetchRecipes(userId)
            }
        }
    }

    fun deleteRecipe(recipe: RecipeEntity) {
        viewModelScope.launch {
            repository.deleteRecipeInEntity(recipe)
            fetchRecipes(recipe.userOwnerId)
        }
    }

    fun countRecipes(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val count = repository.getRecipeCountByUser(userId)
                _recipeCount.postValue(count)
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error counting recipes", e)
            }
        }
    }

    fun fetchFavRecipes(userId: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipesFav = repository.getFavoriteRecipesByUser(userId)
                _recipesFav.postValue(recipesFav)
                Log.d("RecipeViewModel", "Recipes Favs loaded")
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching favs recipes", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchRecipesByTime(userId: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipes = repository.getRecipesByTime(userId)
                _recipesByTime.postValue(recipes)
                Log.d("RecipeViewModel", "Recipes sorted by time loaded")
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipes by time", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun fetchRecipeByDetail(recipeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipe = repository.getRecipeById(recipeId)
                _recipeDetail.value = recipe
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipe detail", e)
            }
        }
    }

    fun fetchIngredientsByRecipe(recipeId: Int) {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val ingredients = repository.getIngredientsForRecipe(recipeId)
                _ingredients.postValue(ingredients)
                Log.d("RecipeViewModel", "Ingredients loaded")
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching ingredients", e)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun insertIngredient(ingredient: IngredientEntity) {
        viewModelScope.launch {
            repository.insertIngredientInEntity(ingredient)
            fetchIngredientsByRecipe(ingredient.recipeOwnerId)
        }
    }

    private fun validateInputs(recipeName: String, description: String, time: String): Boolean {
        if (recipeName.isBlank() || description.isBlank() || time.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return false
        }
        return true
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
