package com.notacompany.myaffairs.ui.task_card

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.AppApplication
import com.notacompany.myaffairs.data.model.Project
import com.notacompany.myaffairs.data.model.Task
import com.notacompany.myaffairs.ui.projects.ProjectViewModel
import com.notacompany.myaffairs.ui.projects.ProjectViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class TaskCardFragment : Fragment(R.layout.task_card_fragment) {

    private val projectViewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var addBtn: FloatingActionButton
    private lateinit var nameEdit: EditText
    private lateinit var deadline: TextView
    private lateinit var setTimeBtn: Button
    private var cal = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var project: Project

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initToolbar(view)
        initDatePicker()
        setUpClickListeners()
    }

    private fun initViews(view: View) {
        addBtn = view.findViewById(R.id.add_button)
        nameEdit = view.findViewById(R.id.task_name)
        deadline = view.findViewById(R.id.deadline)
        setTimeBtn = view.findViewById(R.id.set_time_btn)
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.task_card_toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
    }

    private fun initDatePicker() {
        dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            deadline.text = sdf.format(cal.time)
        }
    }

    private fun setUpClickListeners() {
        addBtn.setOnClickListener {
            createTask()
            activity?.onBackPressed()
        }
        setTimeBtn.setOnClickListener {
            DatePickerDialog(requireActivity(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun createTask() {
        project = projectViewModel.getProject()
        val name = nameEdit.text.toString()
        val deadline = deadline.text.toString()
        if (!TextUtils.isEmpty(name)) {
            projectViewModel.insertTask(Task(null, name, deadline, project.id))

        }
    }
}