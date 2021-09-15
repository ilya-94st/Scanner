package com.example.scanner.DataBases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_users")
class User(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name: String,
    val email: String,
    val phone: String,
    val image: String
) {
}