package com.example.myapplication.ui.feactures.todo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentTodoBinding
import com.example.myapplication.ui.common.adapter.TodoAdapter
import com.example.myapplication.ui.feactures.MainActivity
import com.example.myapplication.ui.feactures.factory.TodoViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private var isFabMenuOpen = false
    private lateinit var adapter: TodoAdapter

    private val todoFragmentViewModel: TodoFragmentViewModel by activityViewModels {
        TodoViewModelFactory((requireActivity() as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFloatingActionButtons()
        loadAllTasks()
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(todoFragmentViewModel, requireContext())
        binding.todoRecyclerView.adapter = adapter
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupFloatingActionButtons() {
        binding.mainFab.setOnClickListener {
            toggleFabMenu()
        }

        listOf(binding.fab1, binding.fab2, binding.fab3, binding.fab4, binding.fab5).forEachIndexed { index, fab ->
            fab.setOnClickListener {
                when(fab.id) {
                    R.id.fab1 -> loadAllTasks()
                    R.id.fab2 -> showCompletedTasks()
                    R.id.fab3 -> showInProgressTasks()
                    R.id.fab4 -> showDeletedTasks()
                    R.id.fab5 -> openNewActivity()
                }
                toggleFabMenu()
            }
        }
    }

    private fun openNewActivity() {
        val intent = Intent(requireContext(), TodoDetailActivity::class.java)
        startActivity(intent)
    }

    private fun loadAllTasks() {
        lifecycleScope.launch {
            todoFragmentViewModel.allTasks.collectLatest { tasks ->
                adapter.submitList(tasks)
            }
        }
    }

    private fun showCompletedTasks() {
        lifecycleScope.launch {
            todoFragmentViewModel.getCompletedTasks().collectLatest { tasks ->
                adapter.submitList(tasks)
            }
        }
    }

    private fun showInProgressTasks() {
        lifecycleScope.launch {
            todoFragmentViewModel.getInProgressTasks().collectLatest { tasks ->
                adapter.submitList(tasks)
            }
        }
    }

    private fun showDeletedTasks() {
        lifecycleScope.launch {
            todoFragmentViewModel.getDeletedTasks().collectLatest { tasks ->
                adapter.submitList(tasks)
            }
        }
    }

    private fun toggleFabMenu() {
        isFabMenuOpen = !isFabMenuOpen
        if (isFabMenuOpen) {
            showVerticalMenu()
        } else {
            hideVerticalMenu()
        }
    }

    private fun showVerticalMenu() {
        listOf(binding.fab1, binding.fab2, binding.fab3, binding.fab4, binding.fab5).forEachIndexed { index, fab ->
            fab.visibility = View.VISIBLE
            fab.alpha = 0f
            fab.translationY = (index + 1) * 50f
            fab.scaleX = 0.5f
            fab.scaleY = 0.5f

            fab.animate()
                .translationY(0f)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(200)
                .setStartDelay((index * 50).toLong())
                .start()
        }
    }

    private fun hideVerticalMenu() {
        listOf(binding.fab1, binding.fab2, binding.fab3, binding.fab4, binding.fab5).reversed().forEachIndexed { index, fab ->
            fab.animate()
                .translationY((index + 1) * 50f)
                .alpha(0f)
                .scaleX(0.5f)
                .scaleY(0.5f)
                .setDuration(200)
                .setStartDelay((index * 30).toLong())
                .withEndAction { fab.visibility = View.INVISIBLE }
                .start()
        }
    }
    override fun onResume() {
        super.onResume()
        loadAllTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}