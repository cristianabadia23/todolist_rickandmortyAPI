package com.example.myapplication.ui.feactures

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.R
import com.example.myapplication.data.local.room.RickAndMortyBase
import com.example.myapplication.data.local.room.TodoDatabase
import com.example.myapplication.data.repository.RickAndMortyRepository
import com.example.myapplication.data.repository.TodoRepository
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.ui.feactures.factory.RickAndMortyFAVViewModelFactory
import com.example.myapplication.ui.feactures.factory.RickAndMortyViewModelFactory
import com.example.myapplication.ui.feactures.factory.TodoViewModelFactory
import com.example.myapplication.ui.feactures.rickAndMorty.RickAndMortyViewModel
import com.example.myapplication.ui.feactures.rickandmortyfav.RickAndMortyFAVViewModel
import com.example.myapplication.ui.feactures.todo.TodoDetailActivity
import com.example.myapplication.ui.feactures.todo.TodoFragmentViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var database: TodoDatabase
    lateinit var repository: TodoRepository
    lateinit var todoFragmentViewModel: TodoFragmentViewModel
    lateinit var rickDatabase: RickAndMortyBase
    lateinit var rickAndMortyViewModel: RickAndMortyViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var rickAndMortyFAVViewModel: RickAndMortyFAVViewModel
    lateinit var rickAndMortyRepository: RickAndMortyRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = TodoDatabase.Companion.getDatabase(this)
        repository = TodoRepository(database.todoTaskDao())

        val factorytodoViewModel = TodoViewModelFactory(repository)
        todoFragmentViewModel = ViewModelProvider(this, factorytodoViewModel)[TodoFragmentViewModel
        ::class.java]


        rickDatabase = RickAndMortyBase.Companion.getDatabase(this)
        rickAndMortyRepository = RickAndMortyRepository(rickDatabase.rickAndMortyDAO())
        val factoryRickAndMorty = RickAndMortyViewModelFactory(rickAndMortyRepository)
        rickAndMortyViewModel = ViewModelProvider(this, factoryRickAndMorty)[RickAndMortyViewModel
        ::class.java]

        val rickAndMortyFAVViewModelFactory =
            RickAndMortyFAVViewModelFactory(rickAndMortyRepository)
        rickAndMortyFAVViewModel = ViewModelProvider(
            this,
            rickAndMortyFAVViewModelFactory
        )[RickAndMortyFAVViewModel
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