package com.notacompany.myaffairs.ui.my_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform
import com.notacompany.myaffairs.R

class MyTasksFragment : Fragment(R.layout.my_tasks_fragment) {

    private lateinit var  addTaskBtn: FloatingActionButton

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initToolbar(view)
        setUpClickListener()
    }

    private fun initViews(view: View) {
        addTaskBtn = view.findViewById(R.id.add_my_task_button)
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_tasks_toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_my_tasks,
            R.id.navigation_projects))
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
    }

    private fun setUpClickListener() {
        addTaskBtn.setOnClickListener {
            findNavController().navigate(R.id.action_myTasks_to_taskCard) }
    }
}