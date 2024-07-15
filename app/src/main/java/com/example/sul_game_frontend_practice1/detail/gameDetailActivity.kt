package com.example.sul_game_frontend_practice1.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sul_game_frontend_practice1.databinding.ActivityGamecreateBinding
import com.example.sul_game_frontend_practice1.databinding.ActivityGamedetailBinding

class gameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGamedetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamedetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }}