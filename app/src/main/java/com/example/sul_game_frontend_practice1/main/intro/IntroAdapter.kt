package com.example.guhaejo.main.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.IntroListItemBinding

class IntroAdapter(val intro_list: ArrayList<Intro>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            IntroListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IntroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        // 아이템 max 수를 5로 제한
        val itemCnt = if (intro_list.size > 5) {
            5
        } else {
            intro_list.size
        }

        return itemCnt
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is IntroViewHolder) {
            holder.intro_name.text = "${position + 1}. ${intro_list[position].intro_name}"
            holder.intro_detail.text = intro_list[position].intro_detail
            holder.heart_cnt.text = intro_list[position].heart_cnt.toString()
        }
    }
}