package com.example.sul_game_frontend_practice1.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.data.Post
import com.example.sul_game_frontend_practice1.databinding.TrendListItemBinding

class TrendAdapter(val postList: List<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val maxItemCnt : Int = 3

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = TrendListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(postList.size > maxItemCnt)
            return maxItemCnt
        return postList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if(holder is TrendViewHolder) {
            holder.title.text = "${position + 1}. ${postList[position].title}"
            holder.content.text = postList[position].content
            holder.heart.text = postList[position].heartCnt.toString()
        }
    }

}

class TrendViewHolder(val binding : TrendListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val title = binding.tvTitleTrendItem
    val content = binding.tvContentTrendItem
    val heart = binding.tvHeartTrendItem
}