package com.example.sul_game_frontend_practice1.recyclerview.LiveChart

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.ItemChartRecyclerviewBinding

class LiveChartViewHolder(binding: ItemChartRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val image = binding.ivImageChartItem
    val rank = binding.tvRankChartItem
    val title = binding.tvTitleChartItem
    val contents = binding.tvContentsChartItem
    val cntHeart = binding.tvHeartChartItem
}