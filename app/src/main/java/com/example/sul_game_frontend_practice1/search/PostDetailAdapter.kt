package com.example.sul_game_frontend_practice1.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sul_game_frontend_practice1.data.Post
import com.example.sul_game_frontend_practice1.databinding.PostDetailListItemBinding
import java.time.format.DateTimeFormatter

class PostDetailAdapter(val postList: List<Post>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = PostDetailListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostDetailViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is PostDetailViewHolder){
            val dateFormatter = DateTimeFormatter.ofPattern("MM/dd HH:mm")
            val dateFormatted = postList[position].createdAt.format(dateFormatter)

            holder.title.text = postList[position].title
            holder.date.text = dateFormatted
            Glide.with(holder.itemView.context)
                .load(postList[position].writer.profileImage)
                .into(holder.profileImage)
            holder.schoolName.text = postList[position].writer.organization
            holder.writer.text = postList[position].writer.name
        }
    }

    inner class PostDetailViewHolder(val binding : PostDetailListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var title = binding.tvTitlePostDetailItem
        var date = binding.tvDatePostDetailItem
        var profileImage = binding.civProfileImagePostDetailItem
        var schoolName = binding.tvSchoolNamePostDetailItem
        var writer = binding.tvWriterPostDetailItem
    }
}

