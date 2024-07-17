package com.example.sul_game_frontend_practice1.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sul_game_frontend_practice1.databinding.ActivityEditProfileImageBinding

class EditProfileImageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}