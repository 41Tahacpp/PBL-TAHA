package com.example.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.marvel.adapters.CharacterAdapter
import com.example.marvel.database.FavoriteCharacter
import com.example.marvel.database.MarvelDatabase
import com.example.marvel.databinding.FragmentFavoritesBinding
import com.example.marvel.models.MarvelCharacter
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: MarvelDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        db = MarvelDatabase.getInstance(requireContext())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadFavorites()

        return binding.root
    }

    private fun loadFavorites() {
        lifecycleScope.launch {
            val favorites = db.characterDao().getAllFavorites()
            val characters = favorites.map {
                MarvelCharacter(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    thumbnail = com.example.marvel.models.Thumbnail(it.imageUrl.substringBeforeLast('.'), it.imageUrl.substringAfterLast('.'))
                )
            }
            binding.recyclerView.adapter = CharacterAdapter(characters)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
