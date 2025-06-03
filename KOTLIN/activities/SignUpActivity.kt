package com.example.marvel.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpButton.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Save user credentials to SharedPreferences
                val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putString("username", email)   // Save email as username
                    putString("password", password)
                    putString("name", name)
                    apply()
                }
                Toast.makeText(this, "Sign up successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            }
        }

        binding.alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}
