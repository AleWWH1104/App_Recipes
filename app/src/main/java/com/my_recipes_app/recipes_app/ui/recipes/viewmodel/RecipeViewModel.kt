package com.my_recipes_app.recipes_app.ui.recipes.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity
import com.my_recipes_app.recipes_app.database.recipes.UsersWithRecipes
import com.my_recipes_app.recipes_app.ui.recipes.repository.RecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

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

    private val _recipeCount = MutableLiveData<Int>()
    val recipeCount: LiveData<Int> = _recipeCount

    private val _recipeDetail = MutableLiveData<RecipeEntity>()
    val recipeDetail: LiveData<RecipeEntity> = _recipeDetail

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

    fun fetchRecipeDetail(recipeId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val recipe = repository.getRecipeDetail(recipeId)
                Log.d("RecipeViewModel", "Fetched recipe image: ${recipe.image}")
                _recipeDetail.postValue(recipe)
            }catch (e: Exception) {
                Log.e("RecipeViewModel", "Error fetching recipe detail: ${e.message}")
            }
        }
    }

    fun insertRecipe(userId: Int, name: String, time: String, isFavorite: Boolean, image: String?, description: String) {
        if (validateInputs(name, time, description)) {
            viewModelScope.launch {
                val newRecipe = RecipeEntity(
                    recipeId = 0,
                    userOwnerId = userId,
                    recipeName = name,
                    time = time.toIntOrNull() ?: 0,
                    isFavorite = isFavorite,
                    image = image,
                    description = description
                )
                repository.insertRecipeInEntity(newRecipe)
                _isRecipeAdded.value = true
                fetchRecipes(userId)
            }
        }
    }

    fun deleteRecipe(recipeId: Int, userOwnerId: Int) {
        viewModelScope.launch {
            repository.deleteRecipeInEntity(recipeId)
            fetchRecipes(userOwnerId)
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

    private fun validateInputs(recipeName: String, time: String, description: String): Boolean {
        if (recipeName.isBlank() || time.isBlank() || description.isBlank()) {
            _errorMessage.value = "Todos los campos son obligatorios"
            return false
        }
        return true
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearRecipeAddedFlag() {
        _isRecipeAdded.value = false
    }

    fun updateRecipeImage(recipeId: Int, imageUrl: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipeImage(recipeId, imageUrl)
        }
    }

    fun updateFavoriteStatus(recipeId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.getFavStatus(recipeId, isFavorite)
        }
    }

    fun saveImageToInternalStorage(context: Context, uri: Uri): String? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val directory = File(context.filesDir, "images")
        if (!directory.exists()) {
            directory.mkdirs()
        }
        val file = File(directory, "${System.currentTimeMillis()}.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file.absolutePath
    }
}
