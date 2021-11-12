package com.notacompany.myaffairs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.model.Task

class TasksAdapter: ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)

        return TasksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        holder.onBind(task)
        holder.setUpClickListener()
    }

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView = itemView.findViewById(R.id.item_task_name)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkbox)
        private val deleteBtn: ImageView = itemView.findViewById(R.id.deleteTask_btn)
//        private val deadline: TextView = itemView.findViewById(R.id.task_deadline)

        fun setUpClickListener() {
            checkBox.setOnClickListener {
                if (checkBox.isChecked)
                    deleteBtn.visibility = VISIBLE
                else
                    deleteBtn.visibility = GONE
            }
            deleteBtn.setOnClickListener{

            }
        }

        fun onBind(task: Task) {
            textName.text = task.name
//            deadline.text = task.deadline
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