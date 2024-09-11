package com.example.sul_game_frontend_practice1.recyclerview.Game

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.ItemGameRecyclerviewBinding

class GameViewHolder (binding:ItemGameRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    val title = binding.tvTitleGameItem
    val contents = binding.tvContentsGameItem
    val userName = binding.tvUsernameGameItem
    val cntHeart = binding.tvHeartGameItem
}