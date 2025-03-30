package com.example.myapplication.ui.todo

import com.example.myapplication.ui.recycler.adapter.TodoAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentTodoBinding
import com.example.myapplication.factory.TodoViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val todoFragmentViewModel: TodoFragmentViewModel by activityViewModels {
        TodoViewModelFactory((requireActivity() as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.todoRecyclerView
        val adapter = context?.let { TodoAdapter(todoFragmentViewModel, it) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)


        lifecycleScope.launch {
            todoFragmentViewModel.allTasks.collectLatest { tasks ->
                adapter?.submitList(tasks)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}