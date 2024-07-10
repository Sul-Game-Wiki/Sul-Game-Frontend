package com.example.guhaejo.main.intro

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.IntroListItemBinding

class IntroViewHolder(binding: IntroListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val intro_name = binding.tvIntroNameIntroItem
    val intro_detail = binding.tvIntroDetailIntroItem
    val heart_cnt = binding.tvHeartIntroItem
}