package com.example.guhaejo.main.notification

import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.AlarmListItemBinding

class AlarmViewHolder(binding: AlarmListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val type = binding.tvAlarmTypeAlarmItem
    val contents = binding.tvAlarmContentsAlarmItem
    val date = binding.tvAlarmDateAlarmItem
}