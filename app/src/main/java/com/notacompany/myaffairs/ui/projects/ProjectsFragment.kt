package com.notacompany.myaffairs.ui.projects

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.AuthActivity
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.adapters.GridSpacingItemDecoration
import com.notacompany.myaffairs.adapters.OnRecyclerProjectClicked
import com.notacompany.myaffairs.adapters.ProjectsAdapter
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.ui.auth.AuthViewModel

class ProjectsFragment : Fragment(R.layout.projects_fragment) {

    private lateinit var authViewModel: AuthViewModel
    private val projectViewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var addProjectButton: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var signOutBtn: Button
    private lateinit var adapter: ProjectsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        initViews(view)
        initToolbar(view)
        setUpClickListener()
        showProjects()
    }

    private fun initViews(view: View) {
        addProjectButton = view.findViewById(R.id.add_project_button)
        signOutBtn = view.findViewById(R.id.sign_out_btn)
        recycler = view.findViewById(R.id.projects_recycler)
        adapter = ProjectsAdapter(clickListener)
        recycler.adapter = adapter
        val spanCount = 2 // columns
        val spacing = 24 // px
        recycler.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing))
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.projects_toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_my_tasks,
            R.id.navigation_projects))
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment,appBarConfiguration)
    }

    private fun setUpClickListener() {
        addProjectButton.setOnClickListener {
            findNavController().navigate(R.id.action_projects_to_projectEdit)
        }

        signOutBtn.setOnClickListener {
            signOut()
        }
    }
    private val clickListener = object : OnRecyclerProjectClicked {
        override fun onClick(project: Project) {
            projectViewModel.setProject(project)
            findNavController().navigate(R.id.action_projects_to_projectCard)
        }
    }

    private fun showProjects() {
        projectViewModel.allProject.observe(requireActivity()) { projects ->
            // Update the cached copy of the words in the adapter.
            projects.let { adapter.submitList(it) }
        }
    }

    private fun signOut() {
        authViewModel.signOut()
        moveToAuthActivity()
    }

    private fun moveToAuthActivity() {
        val intent = Intent(activity, AuthActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }
}