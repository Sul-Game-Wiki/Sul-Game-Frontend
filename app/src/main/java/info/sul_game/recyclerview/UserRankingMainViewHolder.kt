package info.sul_game.recyclerview

import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemChartRecyclerviewBinding
import info.sul_game.databinding.ItemUserRankingRecyclerviewBinding

class UserRankingMainViewHolder (binding: ItemUserRankingRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val image = binding.ivImageUserRankingItem
    val rank = binding.tvRankUserRankingItem
    val strong = binding.tvStrongUserRankingItem
    val name = binding.tvNameUserRankingItem
    val point = binding.tvPointUserRankingItem
}