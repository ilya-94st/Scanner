package com.example.scanner.DataBases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoUsers {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
   suspend fun getAdd(user: User)

   @Query("SELECT * FROM table_users ORDER BY id ASC")
    fun readAll(): LiveData<List<User>>
}