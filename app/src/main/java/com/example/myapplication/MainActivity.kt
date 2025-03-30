package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.factory.TodoDetailActivityViewModelFactory
import com.example.myapplication.factory.TodoViewModelFactory
import com.example.myapplication.persistence.repository.TodoRepository
import com.example.myapplication.persistence.room.TodoDatabase
import com.example.myapplication.ui.todo.TodoDetailActivity
import com.example.myapplication.ui.todo.TodoDetailActivityViewModel
import com.example.myapplication.ui.todo.TodoFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var database: TodoDatabase
    lateinit var repository: TodoRepository
    lateinit var todoFragmentViewModel: TodoFragmentViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = TodoDatabase.getDatabase(this)
        repository = TodoRepository(database.todoTaskDao())

        val factorytodoViewModel = TodoViewModelFactory(repository)
        todoFragmentViewModel = ViewModelProvider(this, factorytodoViewModel)[TodoFragmentViewModel
        ::class.java]



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this@MainActivity, TodoDetailActivity::class.java)
            startActivity(intent)
        }

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}