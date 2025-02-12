package com.my_recipes_app.recipes_app.database.recipes

import androidx.room.*
import androidx.room.ForeignKey.Companion.CASCADE
import com.my_recipes_app.recipes_app.database.users.UserEntity

@Entity(tableName = "recipesDB")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val recipeId: Int = 0,
    @ColumnInfo(name = "recipeName") val recipeName: String,
    @ColumnInfo(name = "userOwner") val userOwnerId: Int, //Aqui la relacion entre entidades
    @ColumnInfo(name = "time") val time: Int,
    @ColumnInfo(name = "isFavorite") val isFavorite: Boolean,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "description") val description: String
)

data class UsersWithRecipes(
    @Embedded val user: UserEntity,
    @Relation(
        parentColumn = "id_user",
        entityColumn = "userOwner"
    )
    val recipes: List<RecipeEntity>
)