package com.example.marvel.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvel.adapters.CharacterAdapter
import com.example.marvel.databinding.FragmentCharactersBinding
import com.example.marvel.models.MarvelCharacter
import com.example.marvel.network.MarvelApiService
import com.example.marvel.utils.ApiUtils
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CharactersFragment : Fragment() {

    private var _binding: FragmentCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterAdapter: CharacterAdapter
    private val characterList = mutableListOf<MarvelCharacter>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersBinding.inflate(inflater, container, false)

        // Setup RecyclerView
        characterAdapter = CharacterAdapter(characterList)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = characterAdapter
        }

        fetchMarvelCharacters()

        return binding.root
    }

    private fun fetchMarvelCharacters() {
        val ts = ApiUtils.getTimestamp()
        val hash = ApiUtils.getHash(ts)
        val apiKey = ApiUtils.getApiKey()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(MarvelApiService::class.java)

        val batchSize = 100
        val totalToFetch = 1000
        var offset = 0

        characterList.clear()  // Clear existing list before fetching

        lifecycleScope.launch {
            try {
                while (offset < totalToFetch) {
                    val response = api.getCharacters(
                        ts = ts,
                        apiKey = apiKey,
                        hash = hash,
                        limit = batchSize,
                        offset = offset
                    )
                    if (response.isSuccessful) {
                        val characters = response.body()?.data?.results ?: emptyList()
                        if (characters.isEmpty()) break  // No more data

                        characterList.addAll(characters)
                        characterAdapter.notifyDataSetChanged()

                        if (characters.size < batchSize) break  // Fewer results than batch size means last batch

                        offset += batchSize
                    } else {
                        Toast.makeText(requireContext(), "Failed to fetch characters", Toast.LENGTH_SHORT).show()
                        break
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
    }

}
