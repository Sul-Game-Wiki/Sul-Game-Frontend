package com.example.sul_game_frontend_practice1.recyclerview.DrinkingGame

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.ItemDrinkingGameRecyclerviewBinding

class DrinkingGameViewHolder(binding: ItemDrinkingGameRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val image = binding.ivImageChartItem
    val title = binding.tvTitleChartItem
    val contents = binding.tvContentsChartItem
    val cntHeart = binding.tvHeartChartItem
    }