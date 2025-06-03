package com.example.marvel.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.marvel.databinding.ActivityCharacterDetailBinding

class CharacterDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name") ?: "N/A"
        val description = intent.getStringExtra("description") ?: "No description available"
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        binding.characterName.text = name
        binding.characterDescription.text = description
        Glide.with(this).load(imageUrl).into(binding.characterImage)
    }
}
