package com.example.marvel.activities

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marvel.databinding.ActivitySignInBinding

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mask password input with dots
        binding.passwordInput.transformationMethod = PasswordTransformationMethod.getInstance()

        binding.signInButton.setOnClickListener {
            val inputUsername = binding.usernameInput.text.toString().trim()
            val inputPassword = binding.passwordInput.text.toString().trim()

            // Read saved credentials from SharedPreferences
            val sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val savedUsername = sharedPref.getString("username", null)
            val savedPassword = sharedPref.getString("password", null)

            if (inputUsername == savedUsername && inputPassword == savedPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        // Go to Sign Up screen
        binding.goToSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }
}
