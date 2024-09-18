package info.sul_game.recyclerview

import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemIntroRecyclerviewBinding
import info.sul_game.databinding.ItemLatestFeedRecyclerviewBinding

class LatestFeedViewHolder (binding: ItemLatestFeedRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    val feedImage = binding.ivFeedImageLatestFeedItem
    val title = binding.tvTitleLatestFeedItem
    val profileImage = binding.civProfileImageLatestFeedItem
    val username = binding.tvUsernameLatestFeedItem
    val cntHeart = binding.tvHeartLatestFeedItem
}