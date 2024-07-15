package com.example.sul_game_frontend_practice1.main.game_intro

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.GameIntroListItemBinding

class GameIntroViewHolder(binding: GameIntroListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val name = binding.tvNameGameIntroItem
    val date = binding.tvDateGameIntroItem
    val school_logo = binding.civSchoolLogoGameIntroItem
    val school_name = binding.tvSchoolNameGameIntroItem
    val user_id = binding.tvIdGameIntroItem
}