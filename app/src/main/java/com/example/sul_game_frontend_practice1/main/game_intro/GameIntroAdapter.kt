package com.example.sul_game_frontend_practice1.main.game_intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.guhaejo.main.intro.Intro
import com.example.guhaejo.main.intro.IntroViewHolder
import com.example.sul_game_frontend_practice1.databinding.GameIntroListItemBinding

class GameIntroAdapter(val game_intro_list: ArrayList<GameIntro>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            GameIntroListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GameIntroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return game_intro_list.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is GameIntroViewHolder) {
            holder.name.text = game_intro_list[position].name
            holder.date.text = game_intro_list[position].date
            Glide.with(holder.itemView.context)
                .load(game_intro_list[position].school_logo)
                .into(holder.school_logo)
            holder.school_name.text = game_intro_list[position].school_name
            holder.user_id.text = game_intro_list[position].user_id
        }
    }
}