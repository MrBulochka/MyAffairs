package com.notacompany.myaffairs.ui.projects

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
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
import java.text.SimpleDateFormat
import java.util.*

class ProjectEditingFragment: Fragment(R.layout.project_editing_fragment) {

    private val projectViewModel: ProjectViewModel by activityViewModels {
        ProjectViewModelFactory((activity?.application as AppApplication).repository)
    }

    private lateinit var editName: EditText
    private lateinit var textDate: TextView
    private lateinit var editDescription: EditText
    private lateinit var addButton: FloatingActionButton
    private var cal = Calendar.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        initToolbar(view)
        initDatePicker()
        setUpClickListener()
    }

    private fun initViews(view: View) {
        editName = view.findViewById(R.id.project_name_edit)
        textDate = view.findViewById(R.id.textView_date)
        editDescription = view.findViewById(R.id.project_description)
        addButton = view.findViewById(R.id.add_button)
    }

    private fun initToolbar(view: View) {
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.project_editing_toolbar)
        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar, navHostFragment)
    }

    private fun initDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            textDate.text = sdf.format(cal.time)
        }
        textDate.text = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

        textDate.setOnClickListener {
            DatePickerDialog(requireActivity(), dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }
    }

    private fun setUpClickListener() {
        addButton.setOnClickListener{
            createProject()
            findNavController().navigate(R.id.action_projectEdit_to_projects)
        }
    }

    private fun createProject() {
        val title = editName.text.toString()
        val deadline = textDate.text.toString()
        val description = editDescription.text.toString()
        if (!TextUtils.isEmpty(title)) {
            projectViewModel.insert(Project(null, title, description, deadline))
        }
    }
}