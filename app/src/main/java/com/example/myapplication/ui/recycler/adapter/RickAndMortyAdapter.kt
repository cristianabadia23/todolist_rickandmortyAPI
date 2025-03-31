package com.example.myapplication.ui.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.persistence.models.RickAndMorty
import com.example.myapplication.ui.recycler.diff.RickAndMortyDiffCallback
import com.example.myapplication.ui.recycler.holder.RickAndMortyViewHolder
import com.example.myapplication.ui.rickAndMorty.RickAndMortyViewModel

class RickAndMortyAdapter(private var characters: List<RickAndMorty>, private val viewModel: RickAndMortyViewModel) : RecyclerView.Adapter<RickAndMortyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickAndMortyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rick_morty_holder_layout, parent, false)
        return RickAndMortyViewHolder(view, viewModel)
    }

    override fun onBindViewHolder(holder: RickAndMortyViewHolder, position: Int) {
        holder.bind(characters[position])
    }

    override fun getItemCount() = characters.size

    fun updateList(newList: List<RickAndMorty>) {
        val diffResult = DiffUtil.calculateDiff(RickAndMortyDiffCallback(characters, newList))
        characters = newList
        diffResult.dispatchUpdatesTo(this)
    }
}