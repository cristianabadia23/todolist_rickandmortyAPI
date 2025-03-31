package com.example.myapplication.ui.feactures.todo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.data.local.room.TodoDatabase
import com.example.myapplication.databinding.ActivityFormTodoBinding
import com.example.myapplication.ui.feactures.factory.TodoDetailActivityViewModelFactory
import com.example.myapplication.data.repository.TodoRepository
import com.example.myapplication.data.local.models.TodoModel

class TodoDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFormTodoBinding
    private lateinit var viewModel: TodoDetailActivityViewModel
    private lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lottieAnimationView = binding.lottieAnimationView

        val database = TodoDatabase.getDatabase(this)
        val repository = TodoRepository(database.todoTaskDao())
        val factory = TodoDetailActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[TodoDetailActivityViewModel::class.java]

        val todoId = intent.getIntExtra("TODO_ID", -1)

        if (todoId != -1) {
            loadExistingTask(todoId)
        } else {
            setupNewTask()
        }
    }

    private fun showSuccessAnimation() {
        lottieAnimationView.visibility = View.VISIBLE
        lottieAnimationView.playAnimation()

        binding.cardView.visibility = View.INVISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            lottieAnimationView.visibility = View.GONE
            binding.cardView.visibility = View.VISIBLE
            finish()
        }, 5000)
    }

    private fun loadExistingTask(todoId: Int) {
        viewModel.getTaskById(todoId) { todoModel ->
            todoModel?.let {
                binding.titleEditText.setText(it.title)
                binding.descriptionEditText.setText(it.description)
                it.date.split(": ").let { parts ->
                    if (parts.size >= 2) {
                        binding.dateEditText.setText(parts[0])
                        binding.hourEditText.setText(parts[1])
                    }
                }
            } ?: run {
                Toast.makeText(this, "Tarea no encontrada", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.saveBotton.setOnClickListener {
            saveTask(todoId)
        }

        binding.deletebotton.setOnClickListener {
            setupDeleteButton(todoId)
        }
    }

    private fun setupDeleteButton(todoId: Int) {
        binding.deletebotton.setOnClickListener {
            showDeleteConfirmationDialog(todoId)
        }
    }

    private fun showDeleteConfirmationDialog(todoId: Int) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que quieres eliminar esta tarea?")
            .setPositiveButton("Eliminar") { _, _ ->
                deleteTask(todoId)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteTask(todoId: Int) {
        viewModel.getTaskById(todoId) { it ->
            viewModel.deleteTask(it!!)
            Toast.makeText(this, "Tarea eliminada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupNewTask() {
        binding.saveBotton.setOnClickListener {
            saveTask(null)
        }
        binding.deletebotton.setOnClickListener {
            finish()
        }
    }

    private fun saveTask(id: Int?) {
        val title = binding.titleEditText.text.toString()
        val description = binding.descriptionEditText.text.toString()
        val date = "${binding.dateEditText.text}: ${binding.hourEditText.text}"

        if (title.isBlank() || description.isBlank()) {
            Toast.makeText(this, "Título y descripción son requeridos", Toast.LENGTH_SHORT).show()
            return
        }

        if (id != null) {
            viewModel.updateTask(TodoModel(
                id = id,
                title = title,
                description = description,
                date = date
            ))
        } else {
            viewModel.insertTask(TodoModel(
                title = title,
                description = description,
                date = date
            ))
        }

        showSuccessAnimation()
    }
}