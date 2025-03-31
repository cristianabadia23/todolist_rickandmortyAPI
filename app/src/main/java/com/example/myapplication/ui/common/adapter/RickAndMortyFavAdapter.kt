package com.example.myapplication.ui.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.local.models.RickAndMortyEntity
import com.example.myapplication.ui.common.holder.RickAndMortyFAVHolder

class RickAndMortyFavAdapter(private val characters: List<RickAndMortyEntity>) :
    RecyclerView.Adapter<RickAndMortyFAVHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RickAndMortyFAVHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rick_morty_holder_layout, parent, false)
        return RickAndMortyFAVHolder(view)
    }

    override fun onBindViewHolder(holder: RickAndMortyFAVHolder, position: Int) {
        val character = characters[position]
        holder.name.text = character.name
        holder.species.text = character.species
        holder.status.text = character.status
        Glide.with(holder.itemView.context).load(character.image).into(holder.image)
        holder.favButton.setImageResource(if (character.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }

    override fun getItemCount() = characters.size
}