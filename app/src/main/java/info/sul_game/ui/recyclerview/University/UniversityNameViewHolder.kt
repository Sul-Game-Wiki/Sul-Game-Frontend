package com.example.sul_game_frontend_practice1.recyclerview.University

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.ItemUniversityNameRecyclerviewBinding
import com.example.sul_game_frontend_practice1.databinding.ItemUniversitySectionRecyclerviewBinding

class UniversityNameViewHolder(binding: ItemUniversityNameRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val university_name = binding.tvNameUniversityNameItem
}