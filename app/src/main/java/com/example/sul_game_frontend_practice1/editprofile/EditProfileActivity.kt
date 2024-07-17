package com.example.sul_game_frontend_practice1.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sul_game_frontend_practice1.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}