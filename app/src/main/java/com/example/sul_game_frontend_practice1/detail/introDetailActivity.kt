package com.example.sul_game_frontend_practice1.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ActivityGamecreateBinding
import com.example.sul_game_frontend_practice1.databinding.ActivityIntrodetailBinding

class introDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntrodetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntrodetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }}