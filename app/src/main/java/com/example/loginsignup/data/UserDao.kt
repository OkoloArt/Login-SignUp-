package com.example.loginsignup.data

import androidx.room.*


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Query("SELECT * FROM user WHERE email = :email AND password = :password ")
    suspend fun getUser(email: String, password:String): User?
}