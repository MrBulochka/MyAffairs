package com.notacompany.myaffairs.ui.projects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.adapters.GridSpacingItemDecoration
import com.notacompany.myaffairs.adapters.ProjectsAdapter
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.ui.SharedViewModel
import com.notacompany.myaffairs.ui.auth.AuthViewModel
import java.util.*

class ProjectsFragment : Fragment(R.layout.fragment_projects) {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var authViewModel: AuthViewModel
    private val projectViewModel: ProjectViewModel by viewModels {
        ProjectViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var addProjectButton: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ProjectsAdapter
    private lateinit var mProjectList: ArrayList<Project>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        initViews(view)
        initNavigation(view)
        setUpClickListener()
        showProjects()
        createItemTouchHelper()
    }

    private fun initViews(view: View) {
        addProjectButton = view.findViewById(R.id.add_project_button)

        recycler = view.findViewById(R.id.projects_recycler)
        adapter = ProjectsAdapter(clickListener = this::onProjectClick)
        recycler.adapter = adapter
        val spanCount = 2 // columns
        val spacing = 24 // px
        recycler.addItemDecoration(GridSpacingItemDecoration(spanCount, spacing))
    }

    private fun initNavigation(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.projects_toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_my_tasks,
            R.id.navigation_projects,
            R.id.navigation_notification,
            R.id.navigation_profile
        ))
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment,appBarConfiguration)
    }

    private fun setUpClickListener() {
        addProjectButton.setOnClickListener {
            findNavController().navigate(R.id.action_projects_to_projectAdd)
        }
    }

    private fun onProjectClick(project: Project) {
        sharedViewModel.select(project)
        findNavController().navigate(R.id.action_projects_to_projectCard)
    }

    private fun showProjects() {
        projectViewModel.allProject.observe(requireActivity()) { projects ->
            projects.let { adapter.submitList(it) }
            mProjectList = projects as ArrayList<Project>
        }
    }

    private fun createItemTouchHelper() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.START or ItemTouchHelper.END,
            0) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                moveProject(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        })
        helper.attachToRecyclerView(recycler)
    }

    private fun moveProject(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            // Move down
            for (i in fromPosition until toPosition) {
                Collections.swap(mProjectList, i, i + 1)
                mProjectList[i].position = i
                mProjectList[i + 1].position = i + 1
            }
        } else {
            // Move up
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mProjectList, i, i - 1)
                mProjectList[i].position = i
                mProjectList[i - 1].position = i - 1
            }
        }
        projectViewModel.updateProjectOrder(mProjectList)
    }
}