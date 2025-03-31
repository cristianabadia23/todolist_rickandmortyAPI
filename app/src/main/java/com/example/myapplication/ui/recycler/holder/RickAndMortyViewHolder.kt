package com.example.myapplication.ui.recycler.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.persistence.models.RickAndMorty
import com.example.myapplication.ui.rickAndMorty.RickAndMortyViewModel

class RickAndMortyViewHolder(view: View, private val viewModel: RickAndMortyViewModel) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.character_name)
    private val image: ImageView = view.findViewById(R.id.character_image)
    private val species: TextView = view.findViewById(R.id.character_species)
    private val status: TextView = view.findViewById(R.id.character_status)
    private val favButton: ImageView = view.findViewById(R.id.button_favorite)

    fun bind(character: RickAndMorty) {
        name.text = character.name
        species.text = character.species
        status.text = character.status
        Glide.with(image.context).load(character.image).into(image)
        favButton.setOnClickListener {
            viewModel.toggleFavorite(character)
            editFavoriteImage(character)
        }
        editFavoriteImage(character)
    }

    fun editFavoriteImage(character: RickAndMorty){
        favButton.setImageResource(if (character.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border)
    }
}