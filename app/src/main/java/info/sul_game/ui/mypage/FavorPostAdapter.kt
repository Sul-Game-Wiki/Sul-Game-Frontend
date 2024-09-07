package info.sul_game.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemFavorpostRecyclerviewBinding

class FavorPostAdapter(private var item : List<FavorPost>) : RecyclerView.Adapter<FavorPostAdapter.FavorPostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorPostHolder {
        val binding = ItemFavorpostRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavorPostHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(
        holder: FavorPostHolder,
        position: Int
    ) {
        holder.tvTitle.text = item[position].title
        holder.tvSentence.text = item[position].sentence
        holder.tvNickname.text = item[position].writer
    }

    inner class FavorPostHolder(binding: ItemFavorpostRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitleFavorpostItem
        val tvSentence = binding.tvSentenceFavorpostItem
        val tvNickname = binding.tvNicknameFavorpostItem
        val ivFavor = binding.ivFavoriteFavorpostItem
    }
}