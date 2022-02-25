package com.example.loginsignup

import android.app.Application
import com.example.loginsignup.data.UserDatabase

class UserApplication : Application() {

    val database: UserDatabase by lazy { UserDatabase.getDatabase(this) }
}