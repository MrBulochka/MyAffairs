package com.notacompany.myaffairs.ui.task_card

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
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

    private lateinit var addBtn: Button
    private lateinit var nameEdit: EditText
    private lateinit var deadline: TextView
    private lateinit var setTimeBtn: Button
    private var calendar = Calendar.getInstance()
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
        nameEdit.showKeyboard()
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
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            deadline.text = sdf.format(calendar.time)
        }

    }

    private fun setUpClickListeners() {
        addBtn.setOnClickListener {
            createTask()
            activity?.onBackPressed()
        }

        setTimeBtn.setOnClickListener {
            DatePickerDialog(requireActivity(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show()

            Log.d("setTime", "${calendar.time}")
            deadline.visibility = VISIBLE
        }
    }

    private fun View.showKeyboard() {
        this.requestFocus()
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun createTask() {
        project = projectViewModel.getProject()
        val name = nameEdit.text.toString()
        var date = 0L
        if (deadline.isVisible)
            date = calendar.timeInMillis
        val position = projectViewModel.getTaskPosition()
        if (!TextUtils.isEmpty(name)) {
            projectViewModel.insertTask(Task(null, name, date, false, project.id, position))

        }
    }
}