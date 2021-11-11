package com.notacompany.myaffairs.adapters

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.notacompany.myaffairs.R
import com.notacompany.myaffairs.data.model.Project


class ProjectsAdapter(
    private val clickListener: OnRecyclerProjectClicked
    ): ListAdapter<Project, ProjectsAdapter.ProjectsViewHolder>(ProjectsComparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        val layoutParams = itemView.layoutParams
        layoutParams.height = (parent.height / 3.5).toInt()
        itemView.layoutParams = layoutParams

        return ProjectsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val project = getItem(position)
        holder.onBind(project)
        holder.itemView.setOnClickListener {
            clickListener.onClick(project)
        }
    }

    class ProjectsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textTitle: TextView? = itemView.findViewById(R.id.project_title)
        private val deadline: TextView = itemView.findViewById(R.id.project_deadline)
        private val description: TextView = itemView.findViewById(R.id.project_description)

        fun onBind(project: Project) {
            itemView.height
            textTitle?.text = project.title
            deadline.text = project.deadline
            description.text = project.description
        }
    }

    class ProjectsComparator : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Project, newItem: Project): Boolean {
            return oldItem == newItem
        }
    }
}

interface OnRecyclerProjectClicked {
    fun onClick(project: Project)
}

class GridSpacingItemDecoration(private val spanCount: Int, private val spacing: Int) :
    ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view) // item position
        val column = position % spanCount // item column
        outRect.left = spacing - column * spacing / spanCount
        outRect.right = (column + 1) * spacing / spanCount
        if (position < spanCount)
        // top edge
            outRect.top = spacing
        // item bottom
        outRect.bottom = spacing
    }
}