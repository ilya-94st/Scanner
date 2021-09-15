package com.example.scanner.DataBases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UsersViewModel(aplication: Application): AndroidViewModel(aplication) {
    val readAllData: LiveData<List<User>>
   private val repository: UserRepository

   init {
       val userDao = UserDataBase.getDataBase(aplication).userDao()
       repository = UserRepository(userDao)
       readAllData = repository.readAllData
   }
    fun addUser(user: User) {
        GlobalScope.launch(Dispatchers.IO) {
            repository.addUsers(user)
        }
    }
}