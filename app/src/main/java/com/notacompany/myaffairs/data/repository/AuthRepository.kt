package com.notacompany.myaffairs.data.repository

import com.notacompany.myaffairs.dataSource.FirebaseAuthSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class AuthRepository {

    fun currentUser(): Boolean {
        return FirebaseAuthSource().getCurrentUser() != null
    }

    fun currentUid(): String {
        return FirebaseAuthSource().getCurrentUid()
    }

    suspend fun registration(email: String?, password: String?) {
        FirebaseAuthSource().registration(email, password)
    }

    suspend fun signIn(email: String?, password: String?) {
        FirebaseAuthSource().signIn(email, password)
    }

    fun signOut() {
        FirebaseAuthSource().signOut()
    }

}