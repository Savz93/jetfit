package com.example.jetfit.data

import com.example.jetfit.util.Resource
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun registerUser(email: String, password: String): Resource<Boolean> {
        return try {
            auth.createUserWithEmailAndPassword(email, password)
            Resource.Success(true)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}