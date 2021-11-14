package com.notacompany.myaffairs.ui.projects

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Project
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.notacompany.myaffairs.adapters.OnTaskClickListener
import com.notacompany.myaffairs.adapters.TasksAdapter
import com.notacompany.myaffairs.data.model.Task


class ProjectCardFragment : Fragment(R.layout.project_card_fragment) {

    private val projectViewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var textDeadline: TextView
    private lateinit var textDescription: TextView
    private lateinit var addButton: Button
    private lateinit var project: Project
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var editIcon: ImageButton
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TasksAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initViews(view)
        initToolbar(view)
        showProject()
        setUpClickListener()
    }

    private fun initViews(view: View) {
        textDeadline = view.findViewById(R.id.project_text_deadline)
        textDescription = view.findViewById(R.id.project_text_description)
        addButton = view.findViewById(R.id.add_button)
        editIcon = view.findViewById(R.id.edit_icon)
        recycler = view.findViewById(R.id.projectTasks_recycler)
        adapter = TasksAdapter(clickListener)
        recycler.adapter = adapter
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
                    projectViewModel.deleteProject(project)
                    projectViewModel.getProjectTasks(project.id)
                    activity?.onBackPressed()
                }
            }
            false
        }
    }
    private val clickListener = object : OnTaskClickListener {
        override fun onCheckboxClick(task: Task, deleteBtn: ImageView, checkBox: CheckBox) {
            if (checkBox.isChecked) {
                deleteBtn.visibility = View.VISIBLE
                val completeTask = Task(task.taskId, task.name, task.deadline, true, task.projectId)
                projectViewModel.updateTask(completeTask)
            }else {
                deleteBtn.visibility = View.GONE
                val completeTask = Task(task.taskId, task.name, task.deadline, false, task.projectId)
                projectViewModel.updateTask(completeTask)
            }
        }

        override fun onDeleteBtnClick(task: Task) {
            projectViewModel.deleteTask(task)
            projectViewModel.getProjectTasks(project.id)
        }
    }

    private fun showProject() {
        project = projectViewModel.getProject()
        toolbar.title = project.title
        textDescription.text = project.description
        textDeadline.text = project.deadline
        showTasks(project.id)
    }

    private fun showTasks(id: Long?) {
        projectViewModel.getProjectTasks(id)
        projectViewModel.projectTasks.observe(requireActivity()) { tasks ->
            tasks.let { adapter.submitList(it) }
        }
    }
}
