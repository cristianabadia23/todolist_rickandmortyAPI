package com.example.myapplication.ui.rickAndMorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainActivity
import com.example.myapplication.databinding.FragmentRickandmortyBinding
import com.example.myapplication.factory.RickAndMortyViewModelFactory
import com.example.myapplication.ui.recycler.adapter.RickAndMortyAdapter
import kotlin.getValue

class RickAndMortyFragment : Fragment() {

    private var _binding: FragmentRickandmortyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         val rickAndMortyViewModel: RickAndMortyViewModel by activityViewModels {
            RickAndMortyViewModelFactory((requireActivity() as MainActivity).rickAndMortyRepository)
        }

        _binding = FragmentRickandmortyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.rvRickAndMorty
        recyclerView.layoutManager = GridLayoutManager(context,4)

        val adapter = RickAndMortyAdapter(emptyList(), rickAndMortyViewModel)
        recyclerView.adapter = adapter

        rickAndMortyViewModel.characters.observe(context as LifecycleOwner) { characters ->
            adapter.updateList(characters)
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() >= layoutManager.itemCount - 5) {
                    rickAndMortyViewModel.fetchCharacters()
                }
            }
        })

        rickAndMortyViewModel.fetchCharacters()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}