package com.example.myapplication.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.constants.TaskState
import com.example.myapplication.databinding.FragmentTodoBinding
import com.example.myapplication.factory.TodoViewModelFactory
import com.example.myapplication.ui.recycler.adapter.TodoAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TodoAdapter

    private val viewModel: TodoFragmentViewModel by activityViewModels {
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
        setupToolbar()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupToolbar() {
        binding.toolbar.inflateMenu(R.menu.todo_filter)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.filter_all -> {
                    viewModel.setFilter(TaskState.ALL.name)
                    true
                }
                R.id.filter_in_progress -> {
                    viewModel.setFilter(TaskState.IN_PROGRESS.name)
                    true
                }
                R.id.filter_completed -> {
                    viewModel.setFilter(TaskState.COMPLETED.name)
                    true
                }
                R.id.filter_deleted -> {
                    viewModel.setFilter(TaskState.DELETED.name)
                    true
                }
                else -> false
            }.also { result ->
                if (result) updateMenuSelection(menuItem)
            }
        }
    }

    private fun updateMenuSelection(selectedItem: MenuItem) {
        binding.toolbar.menu.apply {
            findItem(R.id.filter_all)?.isChecked = false
            findItem(R.id.filter_in_progress)?.isChecked = false
            findItem(R.id.filter_completed)?.isChecked = false
            findItem(R.id.filter_deleted)?.isChecked = false
            selectedItem.isChecked = true
        }
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(viewModel, requireContext())
        binding.todoRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@TodoFragment.adapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collectLatest { state ->
                adapter.submitList(state.tasks)
                updateFilterSelection(state.currentFilter)
            }
        }
    }

    private fun updateFilterSelection(filter: String) {
        binding.toolbar.menu?.apply {
            findItem(R.id.filter_all)?.isChecked = filter == TaskState.ALL.name
            findItem(R.id.filter_in_progress)?.isChecked = filter == TaskState.IN_PROGRESS.name
            findItem(R.id.filter_completed)?.isChecked = filter == TaskState.COMPLETED.name
            findItem(R.id.filter_deleted)?.isChecked = filter == TaskState.DELETED.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}