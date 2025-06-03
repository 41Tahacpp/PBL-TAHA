package com.example.marvel.activities
import com.example.marvel.fragments.CharactersFragment
import com.example.marvel.fragments.FavoritesFragment
import com.example.marvel.fragments.ProfileFragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.marvel.R
import com.example.marvel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(CharactersFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> replaceFragment(CharactersFragment())
                R.id.nav_favorites -> replaceFragment(FavoritesFragment())
                R.id.nav_profile -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }
}
