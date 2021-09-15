package com.example.scanner.DataBases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDataBase: RoomDatabase() {
    abstract fun userDao(): DaoUsers

    companion object{
        @Volatile
        private var INSTANSE: UserDataBase? = null
        fun getDataBase(context: Context): UserDataBase {
            val templense = INSTANSE
            if(templense != null){
               return templense
            }
          synchronized(this) {
              val instanse = Room.databaseBuilder(
                  context.applicationContext,
                  UserDataBase::class.java,
                  "user_database"
              ).build()
              INSTANSE = instanse
              return instanse
          }
        }
    }
}