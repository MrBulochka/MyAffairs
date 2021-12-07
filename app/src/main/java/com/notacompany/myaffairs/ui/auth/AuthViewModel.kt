package com.notacompany.myaffairs.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notacompany.myaffairs.data.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _isAuthorized = MutableLiveData<Boolean>()
    val isAuthorized: LiveData<Boolean> get() = _isAuthorized

    fun signIn(email: String?, password: String?) {
        viewModelScope.launch {
            AuthRepository().signIn(email, password)
            isAuthenticatedUser()
        }
    }

    fun registration(email: String?, password: String?) {
        viewModelScope.launch {
            AuthRepository().registration(email, password)
            isAuthenticatedUser()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            AuthRepository().signOut()
            isAuthenticatedUser()
        }
    }

    fun isAuthenticatedUser() = _isAuthorized.postValue(AuthRepository().currentUser())}