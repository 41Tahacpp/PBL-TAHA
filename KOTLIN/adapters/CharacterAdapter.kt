package com.example.marvel.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvel.R
import com.example.marvel.activities.CharacterDetailActivity
import com.example.marvel.database.MarvelDatabase
import com.example.marvel.databinding.ItemCharacterBinding
import com.example.marvel.models.MarvelCharacter
import com.example.marvel.models.toFavorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class CharacterAdapter(
    private val characters: List<MarvelCharacter>
) : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characters[position]
        val context = holder.itemView.context

        holder.binding.characterName.text = character.name
        holder.binding.characterDesc.text =
            if (character.description.isNotEmpty()) character.description else "No description"

        Glide.with(context)
            .load(character.thumbnail.getFullUrl())
            .into(holder.binding.characterImage)

        val db = MarvelDatabase.getInstance(context)
        val favDao = db.characterDao()

        // Check favorite status on bind
        CoroutineScope(Dispatchers.IO).launch {
            val isFav = favDao.isFavorite(character.id)
            withContext(Dispatchers.Main) {
                holder.binding.favIcon.setImageResource(
                    if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }
        }

        // Toggle favorite on click
        holder.binding.favIcon.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val isFav = favDao.isFavorite(character.id)
                if (isFav) {
                    favDao.delete(character.toFavorite())
                } else {
                    favDao.insert(character.toFavorite())
                }
                withContext(Dispatchers.Main) {
                    holder.binding.favIcon.setImageResource(
                        if (!isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                    )
                    Toast.makeText(
                        context,
                        if (!isFav) "Added to favorites" else "Removed from favorites",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CharacterDetailActivity::class.java).apply {
                putExtra("name", character.name)
                putExtra("description", character.description)
                putExtra("imageUrl", character.thumbnail.getFullUrl())
            }
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = characters.size
}
