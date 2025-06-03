package com.example.marvel.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            Log.d("SplashActivity", "App started")

            // Inflate layout with viewBinding
            binding = ActivitySplashBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Move to SignInActivity after delay
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000)

        } catch (e: Exception) {
            Log.e("SplashActivity", "Crash in SplashActivity: ${e.localizedMessage}")
            e.printStackTrace()
        }
    }
}
