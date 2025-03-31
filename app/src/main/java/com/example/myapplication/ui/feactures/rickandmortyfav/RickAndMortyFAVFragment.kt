package com.example.myapplication.ui.feactures.rickandmortyfav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.ui.feactures.MainActivity
import com.example.myapplication.databinding.FragmentRickandmortyBinding
import com.example.myapplication.ui.feactures.factory.RickAndMortyViewModelFactory
import com.example.myapplication.ui.common.adapter.RickAndMortyFavAdapter
import kotlin.getValue

class RickAndMortyFAVFragment : Fragment() {

    private var _binding: FragmentRickandmortyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rickAndMortyViewModel: RickAndMortyFAVViewModel by activityViewModels {
            RickAndMortyViewModelFactory((requireActivity() as MainActivity).rickAndMortyRepository)
        }

        _binding = FragmentRickandmortyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.rvRickAndMorty
        recyclerView.layoutManager = GridLayoutManager(requireContext(),4)

        rickAndMortyViewModel.favoriteCharacters.observe(viewLifecycleOwner) { favorites ->
            val adapter = RickAndMortyFavAdapter(favorites)
            recyclerView.adapter = adapter
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}