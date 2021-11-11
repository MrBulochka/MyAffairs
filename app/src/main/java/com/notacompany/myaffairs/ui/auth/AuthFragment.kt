package com.notacompany.myaffairs.ui.auth

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.notacompany.myaffairs.MainActivity
import com.notacompany.myaffairs.R

class AuthFragment : Fragment(R.layout.auth_fragment) {

    private lateinit var authViewModel: AuthViewModel

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var signInBtn: Button
    private lateinit var registerView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        subscribeObservers()
        initViews(view)
        setUpListners()
    }

    private fun initViews(view: View) {
        emailInput = view.findViewById(R.id.mail)
        passwordInput = view.findViewById(R.id.edit_password1)
        signInBtn = view.findViewById(R.id.auth_button)
        registerView = view.findViewById(R.id.switch_view)
    }

    private fun setUpListners() {
        signInBtn.setOnClickListener {
            signIn()
        }

        registerView.setOnClickListener {
            findNavController().navigate(R.id.registration) }
    }
    
    private fun subscribeObservers() {
        activity?.let {
            authViewModel.isAuthorized.observe(viewLifecycleOwner, {
            if (it) {
                Log.d(ContentValues.TAG, "Authorized true AuthFragment")
                moveToMainActivity()
            } else {
                Log.d(ContentValues.TAG, "Authorized false AuthFragment")
            }
        })
        }
    }

    private fun moveToMainActivity() {
        val intent = Intent(activity, MainActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun signIn() {
        val email = emailInput.text.toString().trim { it <= ' ' }
        val password = passwordInput.text.toString().trim { it <= ' ' }
        authViewModel.signIn(email, password)

    }
}