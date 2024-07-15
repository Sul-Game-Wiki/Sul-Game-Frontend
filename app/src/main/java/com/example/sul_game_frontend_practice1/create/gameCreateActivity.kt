package com.example.sul_game_frontend_practice1.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ActivityGamecreateBinding
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding

class gameCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamecreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamecreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }}

