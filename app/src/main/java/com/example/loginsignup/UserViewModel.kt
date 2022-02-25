package com.example.loginsignup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.loginsignup.data.User
import com.example.loginsignup.data.UserDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class UserViewModel(private val userDao: UserDao) : ViewModel() {

    private fun insertUser(user: User) {
        viewModelScope.launch {
            userDao.insert(user)
        }
    }

    private fun updateUser(user: User) {
        viewModelScope.launch {
            userDao.update(user)
        }
    }

      suspend fun checkUser(email: String, password: String): User? {
        return userDao.getUser(email, password)
    }

    private fun getUserEntry(name: String, email: String, password: String): User {
        return User(
            name = name,
            email = email,
            password = password
        )
    }

    fun newUser(name: String, email: String, password: String) {
        val newUser = getUserEntry(name, email, password)
        insertUser(newUser)
    }

    fun isEntryValid(name: String, email: String, password: String): Boolean {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            return false
        }
        return true
    }

//    fun checkUser(email: String):User? {
//         getUser(email)
//    }
}

class UserViewModelFactory(private val userDao: UserDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}