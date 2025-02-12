package com.my_recipes_app.recipes_app.database.users

import androidx.room.*

@Entity(tableName = "usersDB")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val userId: Int = 0,
    @ColumnInfo(name = "usernaname") val username: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)