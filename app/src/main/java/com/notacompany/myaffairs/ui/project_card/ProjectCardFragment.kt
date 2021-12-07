package com.notacompany.myaffairs.ui.project_card

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Project
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.notacompany.myaffairs.adapters.TasksAdapter
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.ui.SharedViewModel
import java.util.*


class ProjectCardFragment : Fragment(R.layout.fragment_project_card) {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val projectCardViewModel: ProjectCardViewModel by viewModels {
        ProjectCardViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var textDeadline: TextView
    private lateinit var textDescription: TextView
    private lateinit var addButton: Button
    private lateinit var project: Project
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var editIcon: ImageButton
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: TasksAdapter
    private lateinit var mTaskList: ArrayList<Task>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initViews(view)
        initToolbar(view)
        showProject()
        createItemTouchHelper()
        setUpClickListener()
    }

    private fun initViews(view: View) {
        textDeadline = view.findViewById(R.id.project_text_deadline)
        textDescription = view.findViewById(R.id.project_text_description)
        addButton = view.findViewById(R.id.add_button)
        editIcon = view.findViewById(R.id.edit_icon)

        //init recyclerView
        recycler = view.findViewById(R.id.projectTasks_recycler)
        adapter = TasksAdapter(
            onCheckBoxClick = this::onCheckBoxClick,
            onDeleteBtnClick = this::onDeleteBtnClick)
        recycler.adapter = adapter
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById(R.id.project_card_toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
    }

    private fun setUpClickListener() {
        addButton.setOnClickListener {
            sharedViewModel.setTaskPosition(adapter.itemCount)
            findNavController().navigate(R.id.action_projectCard_to_taskCard)
        }
        editIcon.setOnClickListener {
            findNavController().navigate(R.id.action_projectCard_to_editProject)
        }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.delete -> {
                    projectCardViewModel.deleteProject(project)
                    projectCardViewModel.deleteProjectTasks(project.id)
                    activity?.onBackPressed()
                }
            }
            false
        }
    }

    private fun onCheckBoxClick(task: Task, deleteBtn: ImageView, checkBox: CheckBox) {
        if (checkBox.isChecked) {
            deleteBtn.visibility = View.VISIBLE
            mTaskList[task.position].complete = true
            projectCardViewModel.updateTask(mTaskList[task.position])

        }else {
            deleteBtn.visibility = View.GONE
            mTaskList[task.position].complete = false
            projectCardViewModel.updateTask(mTaskList[task.position])
        }
    }

    private fun onDeleteBtnClick(task: Task) {
        projectCardViewModel.deleteTask(task)
    }

    private fun showProject() {
        sharedViewModel.selected.observe(viewLifecycleOwner) {
            project = it
            toolbar.title = project.title
            textDescription.text = project.description
            textDeadline.text = project.deadline
            projectCardViewModel.getProjectTasks(project)
            showTasks()
        }
    }

    private fun showTasks() {
        projectCardViewModel.projectTasks?.observe(viewLifecycleOwner) { tasks ->
            tasks.let {
                adapter.submitList(it) }
            mTaskList = tasks as ArrayList<Task>
        }
    }

    private fun createItemTouchHelper() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                moveTask(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }
        })
        helper.attachToRecyclerView(recycler)
    }

    private fun moveTask(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            // Move down
            for (i in fromPosition until toPosition) {
                Collections.swap(mTaskList, i, i + 1)
                mTaskList[i].position = i
                mTaskList[i + 1].position = i + 1
            }
        } else {
            // Move up
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mTaskList, i, i - 1)
                mTaskList[i].position = i
                mTaskList[i - 1].position = i - 1
            }
        }
        projectCardViewModel.updateTaskOrder(mTaskList)
    }
}