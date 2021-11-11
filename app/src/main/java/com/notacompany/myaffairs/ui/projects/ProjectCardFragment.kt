package com.notacompany.myaffairs.ui.projects

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Project

import android.view.ViewGroup

import android.view.LayoutInflater
import android.widget.ImageView


class ProjectCardFragment : Fragment(R.layout.project_card_fragment) {

    private val projectViewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var textDeadline: TextView
    private lateinit var textDescription: TextView
    private lateinit var addButton: FloatingActionButton
    private lateinit var project: Project
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var editIcon: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initViews(view)
        initToolbar(view)
        getProject()
        setUpClickListener()
    }

    private fun initViews(view: View) {
        textDeadline = view.findViewById(R.id.project_text_deadline)
        textDescription = view.findViewById(R.id.project_text_description)
        addButton = view.findViewById(R.id.add_button)
        editIcon = view.findViewById(R.id.edit_icon)
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById(R.id.project_card_toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
    }

    private fun setUpClickListener() {
        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_projectCard_to_taskCard)
        }

        editIcon.setOnClickListener {
            findNavController().navigate(R.id.action_projectCard_to_editProject)
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    projectViewModel.delete(project)
                    activity?.onBackPressed()
                }
            }
            false
        }
    }

    private fun getProject() {
        project = projectViewModel.getProject()
        toolbar.title = project.title
        textDescription.text = project.description
        textDeadline.text = project.deadline
    }
}
