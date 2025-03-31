package com.example.myapplication.ui.common.holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class RickAndMortyFAVHolder (view: View) : RecyclerView.ViewHolder(view)  {
    val name: TextView = view.findViewById(R.id.character_name)
    val image: ImageView = view.findViewById(R.id.character_image)
    val species: TextView = view.findViewById(R.id.character_species)
    val status: TextView = view.findViewById(R.id.character_status)
    val favButton: ImageView = view.findViewById(R.id.button_favorite)
}