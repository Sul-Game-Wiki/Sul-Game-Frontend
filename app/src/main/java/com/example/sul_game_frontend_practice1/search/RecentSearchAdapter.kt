package com.example.sul_game_frontend_practice1.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ItemRecentSearchRecyclerviewBinding

class RecentSearchAdapter(private var item : List<String>) : RecyclerView.Adapter<RecentSearchAdapter.RecentSearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchHolder {
        val binding = ItemRecentSearchRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(
        holder: RecentSearchHolder,
        position: Int
    ) {
        holder.textView.text = item[position]
    }

    inner class RecentSearchHolder(binding: ItemRecentSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.tvRecentSearchItem
    }
}