package com.notacompany.myaffairs.ui.my_tasks

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.adapters.TasksAdapter
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Task

class MyTasksFragment: Fragment(R.layout.fragment_my_tasks) {

    private val myTasksViewModel: MyTasksViewModel by viewModels {
        MyTasksViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var  addTaskBtn: FloatingActionButton
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TasksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initNavigation(view)
        setUpClickListener()
        showTasks()
    }

    private fun initViews(view: View) {
        addTaskBtn = view.findViewById(R.id.add_my_task_button)

        recycler = view.findViewById(R.id.my_tasks_recycler)
        adapter = TasksAdapter(
            onCheckBoxClick = this::onCheckBoxClick,
            onDeleteBtnClick = this::onDeleteBtnClick)
        recycler.adapter = adapter
    }

    private fun initNavigation(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.my_tasks_toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_my_tasks,
            R.id.navigation_projects,
            R.id.navigation_notification,
            R.id.navigation_profile
        ))
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
    }

    private fun setUpClickListener() {
        addTaskBtn.setOnClickListener {
            findNavController().navigate(R.id.action_myTasks_to_taskCard) }
    }

    private fun onCheckBoxClick(task: Task, deleteBtn: ImageView, checkBox: CheckBox) {
        if (checkBox.isChecked) {
            deleteBtn.visibility = View.VISIBLE
            task.complete = true
            myTasksViewModel.updateTask(task)

        }else {
            deleteBtn.visibility = View.GONE
            task.complete = false
            myTasksViewModel.updateTask(task)
        }
    }

    private fun onDeleteBtnClick(task: Task) {
        myTasksViewModel.deleteTask(task)
    }

    private fun showTasks() {
        myTasksViewModel.allTasks.observe(viewLifecycleOwner) { tasks ->
            tasks.let {
                adapter.submitList(it)
            }
        }
    }
}