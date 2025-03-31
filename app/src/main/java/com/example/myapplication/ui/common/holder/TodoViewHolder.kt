package com.example.myapplication.ui.common.holder

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.local.models.TodoModel
import com.example.myapplication.utils.TaskState

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.titleText)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionText)
    private val stateTextView: TextView = itemView.findViewById(R.id.stateText)
    private val statusCheckBox: CheckBox = itemView.findViewById(R.id.checkBox)

    fun bind(task: TodoModel, onStatusChanged: (TodoModel) -> Unit) {
        resetTextAppearance()
        statusCheckBox.isEnabled = true

        titleTextView.text = task.title
        descriptionTextView.text = task.description
        updateStateUI(task.state)

        statusCheckBox.isChecked = task.state == TaskState.COMPLETED.name

        statusCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val newState = if (isChecked) TaskState.COMPLETED.name else TaskState.IN_PROGRESS.name
            updateStateUI(newState)
            onStatusChanged(task.copy(state = newState))
        }

        if (task.state == TaskState.DELETED.name) {
            markAsDeleted()
            statusCheckBox.isEnabled = false
        }
    }

    private fun updateStateUI(state: String) {
        stateTextView.text = when(state) {
            TaskState.IN_PROGRESS.name -> "En progreso"
            TaskState.COMPLETED.name -> "Completada"
            TaskState.DELETED.name -> "Eliminada"
            else -> state
        }
        val color = when(state) {
            TaskState.IN_PROGRESS.name -> ContextCompat.getColor(itemView.context, R.color.orange)
            TaskState.COMPLETED.name -> ContextCompat.getColor(itemView.context, R.color.green)
            TaskState.DELETED.name -> ContextCompat.getColor(itemView.context, R.color.red)
            else -> ContextCompat.getColor(itemView.context, R.color.teal_700)
        }
        stateTextView.setTextColor(color)
    }

    fun markAsDeleted() {
        titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        descriptionTextView.paintFlags = descriptionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        stateTextView.paintFlags = stateTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    private fun resetTextAppearance() {
        titleTextView.paintFlags = titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        descriptionTextView.paintFlags = descriptionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        stateTextView.paintFlags = stateTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}