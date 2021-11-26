package com.notacompany.myaffairs.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.notacompany.myaffairs.MainActivity
import com.notacompany.myaffairs.R

class RegistrFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var registerView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View {
        val root: View = inflater.inflate(R.layout.regist_fragment, container, false)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        initViews(root)
        setUpClickListners()
        subscribeObservers()

        return root
    }

    private fun initViews(view: View) {
        emailInput = view.findViewById(R.id.mail)
        passwordInput = view.findViewById(R.id.edit_password1)
        registerBtn = view.findViewById(R.id.reg_button)
        registerView = view.findViewById(R.id.switch_view)
        progressBar = view.findViewById(R.id.progressBar)
    }

    private fun setUpClickListners() {
        registerView.setOnClickListener {
            findNavController().navigate(R.id.authorization) }

        registerBtn.setOnClickListener{
            registration()
            registerBtn.isClickable = false
            progressBar.visibility = VISIBLE
        }
    }

    private fun subscribeObservers() {
        activity?.let {
            authViewModel.isAuthorized.observe(viewLifecycleOwner, {
                if (it) {
                    moveToMainActivity()
                } else {
                    registerBtn.isClickable = true
                    progressBar.visibility = GONE
                    Toast.makeText(this.context, "Что-то пошло не так", Toast.LENGTH_SHORT)
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

    private fun registration() {
        val email = emailInput.text.toString().trim { it <= ' ' }
        val password = passwordInput.text.toString().trim { it <= ' ' }
        authViewModel.registration(email, password) }
}