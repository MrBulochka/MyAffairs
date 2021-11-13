package com.notacompany.myaffairs.ui.auth

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notacompany.myaffairs.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    var isAuthorized = MutableLiveData<Boolean>()

    fun signIn(email: String?, password: String?) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            viewModelScope.launch {
                AuthRepository().signIn(email, password)
                isAuthenticatedUser()
            }
        }
    }

    fun registration(email: String?, password: String?) {
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            viewModelScope.launch {
                AuthRepository().registration(email, password)
                isAuthenticatedUser()
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            AuthRepository().signOut()
            isAuthenticatedUser()
        }
    }

    fun isAuthenticatedUser() = isAuthorized.postValue(AuthRepository().currentUser())}