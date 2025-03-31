package com.example.myapplication.ui.feactures.todo

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
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

        val mainActivity = activity as? MainActivity
        mainActivity?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.visibility =
            View.VISIBLE

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