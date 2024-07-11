package com.example.guhaejo.main.game

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.GameListItemBinding

class GameViewHolder(binding: GameListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val game_name = binding.tvGameNameGameItem
    val heart_cnt = binding.tvHeartGameItem
}