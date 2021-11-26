package com.notacompany.myaffairs.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
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
    private lateinit var progressBar: ProgressBar

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
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun setUpListners() {
        signInBtn.setOnClickListener {
            signIn()
            signInBtn.isClickable = false
            progressBar.visibility = VISIBLE
        }

        registerView.setOnClickListener {
            findNavController().navigate(R.id.registration) }
    }
    
    private fun subscribeObservers() {
        activity?.let {
            authViewModel.isAuthorized.observe(viewLifecycleOwner, {
                if (it) {
                    moveToMainActivity()
                } else {
                    signInBtn.isClickable = true
                    progressBar.visibility = GONE
                    Toast.makeText(this.context, "Что-то пошло не так", LENGTH_SHORT)
                        .show()
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