package com.notacompany.myaffairs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.notacompany.myaffairs.ui.auth.AuthViewModel

class SplashActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        authViewModel.isAuthenticatedUser()
        authViewModel.isAuthorized.observe(this, {
            if (it) {
                moveToMainActivity()
            } else {
                moveToAuthActivity()
            }
        })
    }

    private fun moveToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun moveToAuthActivity() {
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish()
    }
}