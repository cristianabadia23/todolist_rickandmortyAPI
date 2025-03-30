package com.example.myapplication.ui.recycler.adapter

import TodoDiffCallback
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ListAdapter
import com.example.myapplication.R
import com.example.myapplication.persistence.models.TodoModel
import com.example.myapplication.ui.recycler.holder.TodoViewHolder
import com.example.myapplication.ui.todo.TodoDetailActivity
import com.example.myapplication.ui.todo.TodoFragmentViewModel

class TodoAdapter(private val viewModel: TodoFragmentViewModel, private val context: Context) :
    ListAdapter<TodoModel, TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_holder_layout, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val task = getItem(position)

        holder.bind(task) { updatedTask ->
            viewModel.updateTask(updatedTask)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TodoDetailActivity::class.java).apply {
                putExtra("TODO_ID", task.id)
            }
            context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            showDeleteDialog(task, holder)
            true
        }
    }

    private fun showDeleteDialog(task: TodoModel, holder: TodoViewHolder) {
        AlertDialog.Builder(context)
            .setTitle("Eliminar tarea")
            .setMessage("¿Desea eliminar esta tarea?")
            .setPositiveButton("Sí") { _, _ ->
                val updatedTask = task.copy(state = "Eliminada")
                viewModel.updateTask(updatedTask)
                holder.markAsDeleted()
                Toast.makeText(context, "Tarea eliminada", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}