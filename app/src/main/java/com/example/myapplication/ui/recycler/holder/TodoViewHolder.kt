package com.example.myapplication.ui.recycler.holder

import android.graphics.Paint
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.persistence.models.TodoModel

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.titleText)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionText)
    private val stateTextView: TextView = itemView.findViewById(R.id.stateText)
    private val statusCheckBox: CheckBox = itemView.findViewById(R.id.checkBox)

    fun bind(task: TodoModel, onStatusChanged: (TodoModel) -> Unit) {
        titleTextView.text = task.title
        descriptionTextView.text = task.description
        updateStateUI(task.state)

        statusCheckBox.isChecked = task.state == "done"

        statusCheckBox.setOnCheckedChangeListener { _, isChecked ->
            val newState = if (isChecked) "done" else "in progress"
            updateStateUI(newState)
            onStatusChanged(task.copy(state = newState))
        }

        if (task.state == "Eliminada") {
            markAsDeleted()
            statusCheckBox.isEnabled = false
        } else {
            resetTextAppearance()
            statusCheckBox.isEnabled = true
        }
    }

    private fun updateStateUI(state: String) {
        stateTextView.text = when(state) {
            "in progress" -> "En progreso"
            "done" -> "Completada"
            "remove" -> "Eliminada"
            else -> state
        }

        val color = when(state) {
            "in progress" -> ContextCompat.getColor(itemView.context, R.color.orange)
            "done" -> ContextCompat.getColor(itemView.context, R.color.green)
            "remove" -> ContextCompat.getColor(itemView.context, R.color.red)
            else -> ContextCompat.getColor(itemView.context, R.color.teal_700)
        }
        stateTextView.setTextColor(color)
    }

    fun markAsDeleted() {
        titleTextView.paintFlags = titleTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        descriptionTextView.paintFlags = descriptionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        stateTextView.paintFlags = stateTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        updateStateUI("Eliminada")
    }

    private fun resetTextAppearance() {
        titleTextView.paintFlags = titleTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        descriptionTextView.paintFlags = descriptionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        stateTextView.paintFlags = stateTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}