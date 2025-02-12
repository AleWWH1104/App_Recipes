package com.my_recipes_app.recipes_app.database.ingredients

import androidx.room.*
import com.my_recipes_app.recipes_app.database.recipes.RecipeEntity

@Entity(tableName = "ingredientsDB")
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val ingredientId: Int = 0,
    @ColumnInfo(name = "ingredient") val ingredient: String,
    @ColumnInfo(name = "recipeOwner") val recipeOwnerId: Int, //Aqui la relacion entre entidades
    @ColumnInfo(name = "cant") val cant: Int
)

data class RecipesWithIngredients(
    @Embedded val recipe: RecipeEntity,
    @Relation(
        parentColumn = "recipeId",
        entityColumn = "recipeOwner"
    )
    val ingredients: List<IngredientEntity>
)