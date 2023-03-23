package com.example.jetfit.data

import com.example.jetfit.util.Resource

interface AuthRepository {
    fun registerUser(email: String, password: String): Resource<Boolean>
}