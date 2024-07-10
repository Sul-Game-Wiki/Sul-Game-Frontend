package com.example.guhaejo.main.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.GameListItemBinding

class GameAdapter(val game_list: ArrayList<Game>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            GameListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        // 아이템 max 수를 5로 제한
        val itemCnt = if (game_list.size > 5) {
            5
        } else {
            game_list.size
        }

        return itemCnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GameViewHolder) {
            holder.game_name.text = "${position + 1}. ${game_list[position].game_name}"
            holder.heart_cnt.text = game_list[position].heart_cnt.toString()
        }
    }
}