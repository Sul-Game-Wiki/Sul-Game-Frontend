package com.example.sul_game_frontend_practice1.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ActivityGamecreateBinding

import com.example.sul_game_frontend_practice1.databinding.ActivityIntrocreateBinding

class IntroCreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntrocreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntrocreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }}