package com.example.myapplication

import com.example.myapplication.data.local.models.RickAndMorty
import com.example.myapplication.ui.common.diff.RickAndMortyDiffCallback
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RickAndMortyDiffCallbackTest {

    @Test
    fun `areItemsTheSame returns true when ids match`() {
        val oldItem = RickAndMorty(1, "Rick", "image1", "Human", "Alive", false)
        val newItem = RickAndMorty(1, "Rick Sanchez", "image1", "Human", "Alive", true)

        val callback = RickAndMortyDiffCallback(listOf(oldItem), listOf(newItem))

        assertTrue(callback.areItemsTheSame(0, 0))
    }

    @Test
    fun `areItemsTheSame returns false when ids differ`() {
        val oldItem = RickAndMorty(1, "Rick", "image1", "Human", "Alive", false)
        val newItem = RickAndMorty(2, "Morty", "image2", "Human", "Alive", false)

        val callback = RickAndMortyDiffCallback(listOf(oldItem), listOf(newItem))

        assertFalse(callback.areItemsTheSame(0, 0))
    }

    @Test
    fun `areContentsTheSame returns true when all fields match`() {
        val oldItem = RickAndMorty(1, "Rick", "image1", "Human", "Alive", false)
        val newItem = RickAndMorty(1, "Rick", "image1", "Human", "Alive", false)

        val callback = RickAndMortyDiffCallback(listOf(oldItem), listOf(newItem))

        assertTrue(callback.areContentsTheSame(0, 0))
    }

    @Test
    fun `areContentsTheSame returns false when any field differs`() {
        val oldItem = RickAndMorty(1, "Rick", "image1", "Human", "Alive", false)
        val newItem = RickAndMorty(1, "Rick Sanchez", "image1", "Human", "Alive", false)

        val callback = RickAndMortyDiffCallback(listOf(oldItem), listOf(newItem))

        assertFalse(callback.areContentsTheSame(0, 0))
    }
}