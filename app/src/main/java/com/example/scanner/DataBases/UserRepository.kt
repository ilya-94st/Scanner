package com.example.scanner.DataBases

import androidx.lifecycle.LiveData

class UserRepository(private val users: DaoUsers) {
    val readAllData: LiveData<List<User>> = users.readAll()
 suspend  fun addUsers(user: User){
     users.getAdd(user)
 }
}