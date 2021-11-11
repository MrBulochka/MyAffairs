package com.notacompany.myaffairs.dataSource

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class FirebaseAuthSource() {

    private var firebaseAuth = FirebaseAuth.getInstance()

    fun getCurrentUid(): String {
        return firebaseAuth.currentUser!!.uid
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    suspend fun signIn(email: String?, password: String?): AuthResult? {
        return try {
            val data = firebaseAuth
                    .signInWithEmailAndPassword(email!!, password!!)
                    .await()
            data
        }catch (e: Exception){
            null
        }
    }

    suspend fun registration(email: String?, password: String?): AuthResult? {
        return try {
            val data = firebaseAuth
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .await()
            data
        }catch (e: Exception){
            null
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}