package com.example.guhaejo.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sul_game_frontend_practice1.databinding.AlarmListItemBinding

class AlarmAdapter(val alarm_list: ArrayList<Alarm>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            AlarmListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlarmViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return alarm_list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlarmViewHolder) {
            holder.type.text = alarm_list[position].type
            holder.contents.text = alarm_list[position].contents
            holder.date.text = alarm_list[position].date
        }
    }
}