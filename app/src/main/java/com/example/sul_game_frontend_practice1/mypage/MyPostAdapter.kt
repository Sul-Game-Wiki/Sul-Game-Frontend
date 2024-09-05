package com.example.sul_game_frontend_practice1.mypage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ItemMypostRecyclerviewBinding

class MyPostAdapter(private var item : List<MyPost>) : RecyclerView.Adapter<MyPostAdapter.MyPostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPostHolder {
        val binding = ItemMypostRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPostHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(
        holder: MyPostHolder,
        position: Int
    ) {
        holder.tvTitle.text = item[position].title
        holder.tvSentence.text = item[position].introduction
        holder.tvEdit.text = "수정"
    }

    inner class MyPostHolder(binding: ItemMypostRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitleMypostItem
        val tvSentence = binding.tvSentenceMypostItem
        val tvEdit = binding.tvEditMypostItem
    }
}