package com.example.myapplication.ui.common.holder

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.local.models.RickAndMorty
import com.example.myapplication.ui.feactures.rickAndMorty.RickAndMortyViewModel

class RickAndMortyViewHolder(view: View, private val viewModel: RickAndMortyViewModel) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.character_name)
    private val image: ImageView = view.findViewById(R.id.character_image)
    private val species: TextView = view.findViewById(R.id.character_species)
    private val status: TextView = view.findViewById(R.id.character_status)
    private val favButton: ImageButton = view.findViewById(R.id.button_favorite)

    private val animationView: LottieAnimationView = view.findViewById(R.id.character_animation)

    fun bind(character: RickAndMorty) {
        name.text = character.name
        species.text = character.species
        status.text = character.status
        Glide.with(image.context).load(character.image).into(image)

        favButton.setImageResource(
            if (character.isFavorite) R.drawable.ic_favorite
            else R.drawable.baseline_favorite_border_24
        )

        favButton.setOnClickListener {
            val newFavoriteState = !character.isFavorite
            viewModel.toggleFavorite(character)

            favButton.setImageResource(
                if (newFavoriteState) R.drawable.ic_favorite
                else R.drawable.baseline_favorite_border_24
            )

            showLottieAnimation(newFavoriteState)
        }
    }

    private fun showLottieAnimation(isFavorite: Boolean) {
        animationView.apply {
            setAnimation(if (isFavorite) R.raw.fav else R.raw.mortycryanimation)
            visibility = View.VISIBLE
            alpha = 1f
            playAnimation()

            postDelayed({
                animate()
                    .alpha(0f)
                    .setDuration(300)
                    .withEndAction { visibility = View.INVISIBLE }
                    .start()
            }, 5000)
        }
    }
}