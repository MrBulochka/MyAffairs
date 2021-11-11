package com.notacompany.myaffairs.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.model.Task

class TasksAdapter(
    private val clickListener: OnRecyclerTaskClicked
): ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
//        val layoutParams = itemView.layoutParams
//        layoutParams.height = (parent.height / 3.5).toInt()
//        itemView.layoutParams = layoutParams

        return TasksViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = getItem(position)
        holder.onBind(task)
        holder.itemView.setOnClickListener {
            clickListener.onClick(task)
        }
    }

    class TasksViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textName: TextView? = itemView.findViewById(R.id.project_title)
        private val deadline: TextView = itemView.findViewById(R.id.project_deadline)

        fun onBind(task: Task) {
            itemView.height
            textName?.text = task.name
            deadline.text = task.deadline
        }
    }

    class TasksComparator : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnRecyclerTaskClicked {
    fun onClick(task: Task)
}

//class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) :
//    RecyclerView.ItemDecoration() {
//    override fun getItemOffsets(
//        outRect: Rect,
//        view: View,
//        parent: RecyclerView,
//        state: RecyclerView.State
//    ) {
//        val position = parent.getChildAdapterPosition(view) // item position
//        val column = position % spanCount // item column
//        outRect.left = spacing - column * spacing / spanCount
//        outRect.right = (column + 1) * spacing / spanCount
//        if (position < spanCount)
//        // top edge
//            outRect.top = spacing
//        // item bottom
//        outRect.bottom = spacing
//    }
//}