package com.notacompany.myaffairs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.utils.DateAndTimeFormatter
import com.notacompany.myaffairs.data.model.Task

class TasksAdapter(
    private val clickListener: OnTaskClickListener
): ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TasksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        holder.onBind(task)
        holder.checkBox.setOnClickListener {
            clickListener.onCheckboxClick(task, holder.deleteBtn, holder.checkBox)
        }
        holder.deleteBtn.setOnClickListener {
            clickListener.onDeleteBtnClick(task)
        }
    }

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.itemTask_name)
        val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        val deleteBtn: ImageView = itemView.findViewById(R.id.deleteTask_btn)
        private val deadline: TextView = itemView.findViewById(R.id.itemTask_deadline)

        fun onBind(task: Task) {
            textName.text = task.name
            if (task.deadline != 0L)
                deadline.text = DateAndTimeFormatter.getDate(task.deadline)
            checkBox.isChecked = task.complete
            if (task.complete)
                deleteBtn.visibility = VISIBLE
        }
    }

    class TasksComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.taskId == newItem.taskId
        }
        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnTaskClickListener {
    fun onCheckboxClick(task: Task, deleteBtn: ImageView, checkBox: CheckBox)

    fun onDeleteBtnClick(task: Task)
}