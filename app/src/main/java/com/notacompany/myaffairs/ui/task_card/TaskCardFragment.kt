package com.notacompany.myaffairs.ui.task_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.R

class TaskCardFragment : Fragment(R.layout.task_card_fragment) {

    private lateinit var addBtn: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initToolbar(view)
        setUpClickListeners()
    }

    private fun initViews(view: View) {
        addBtn = view.findViewById(R.id.add_button)
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.task_card_toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
    }

    private fun setUpClickListeners() {
        addBtn.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}