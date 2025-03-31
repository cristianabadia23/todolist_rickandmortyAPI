package com.example.myapplication.ui.common.diff

import androidx.recyclerview.widget.DiffUtil
import com.example.myapplication.data.local.models.RickAndMorty

class RickAndMortyDiffCallback(private val oldList: List<RickAndMorty>, private val newList: List<RickAndMorty>) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
