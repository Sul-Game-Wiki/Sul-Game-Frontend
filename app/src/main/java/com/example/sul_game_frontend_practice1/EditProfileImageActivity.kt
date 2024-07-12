package com.example.sul_game_frontend_practice1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sul_game_frontend_practice1.databinding.ActivityEditProfileImageBinding
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding

class EditProfileImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}