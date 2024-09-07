package com.example.sul_game_frontend_practice1.create

import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sul_game_frontend_practice1.databinding.ItemCreateRecyclerviewBinding

class CreateCreateGameAdapter(private val items: MutableList<MediaItem>,private val fragment: CreateCreateFragment): RecyclerView.Adapter<CreateCreateGameAdapter.CreateCreateAdapterViewHoler>() {
    data class MediaItem(val uri: Uri, val isVideo: Boolean)
    private var videoItemCnt = 0
    private var videoExists = false
    fun isVideoExist(): Boolean{
        if(videoItemCnt<=0){
            videoExists=false
        }else{videoExists=true}
        return videoExists
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreateCreateGameAdapter.CreateCreateAdapterViewHoler {
        val binding = ItemCreateRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return CreateCreateAdapterViewHoler(binding)
    }

    override fun onBindViewHolder(
        holder: CreateCreateGameAdapter.CreateCreateAdapterViewHoler,
        position: Int
    ) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun addMedia(uri:Uri,isVideo: Boolean):Boolean{


        items.add(MediaItem(uri,isVideo))
        notifyItemInserted(items.size -1)
        if(isVideo==true){

            videoItemCnt++
        }
        return true
    }

    fun removeMedia(position: Int){
        if(items[position].isVideo){
            videoItemCnt=0
        }
        items.removeAt(position)
        notifyItemRemoved(position)
        fragment.checkRecyclerViewVisibility()
    }
    inner class CreateCreateAdapterViewHoler(private val binding: ItemCreateRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MediaItem){
            if(item.isVideo){
                binding.ivVideoIconItemCreateRv.visibility = View.VISIBLE
                Glide.with(binding.ivThumnailItemCreateRv.context)
                    .asBitmap()
                    .load(item.uri)
                    .into(binding.ivThumnailItemCreateRv)
            }else{
                binding.ivVideoIconItemCreateRv.visibility = View.GONE
                Glide.with(binding.ivThumnailItemCreateRv.context)
                    .load(item.uri)
                    .into(binding.ivThumnailItemCreateRv)



            }
            binding.btnDeleteItemCreateRv.setOnClickListener {
                removeMedia(adapterPosition)

            }
            binding.btnDeleteItemCreateRv.visibility = View.VISIBLE
        }
    }
}