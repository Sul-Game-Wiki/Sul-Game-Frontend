package info.sul_game.recyclerview

import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemIntroRecyclerviewBinding

class IntroMainViewHolder (binding: ItemIntroRecyclerviewBinding): RecyclerView.ViewHolder(binding.root) {
    val title = binding.tvTitleIntroItem
    val contents = binding.tvContentsIntroItem
    val userName = binding.tvUsernameIntroItem
    val cntHeart = binding.tvHeartIntroItem
}