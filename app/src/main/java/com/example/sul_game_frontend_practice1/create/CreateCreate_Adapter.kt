package com.example.sul_game_frontend_practice1.create

import android.media.ThumbnailUtils
import android.media.browse.MediaBrowser.MediaItem
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sul_game_frontend_practice1.databinding.ItemCreateRecyclerviewBinding

class CreateCreate_Adapter(private val items: MutableList<MediaItem>): RecyclerView.Adapter<CreateCreate_Adapter.CreateCreateAdapterViewHoler>() {
    data class MediaItem(val uri: Uri, val isVideo: Boolean)
    private var videoExists = false
    fun doesVideoExist(): Boolean{
        return videoExists
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CreateCreate_Adapter.CreateCreateAdapterViewHoler {
        val binding = ItemCreateRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    return CreateCreateAdapterViewHoler(binding)
    }

    override fun onBindViewHolder(
        holder: CreateCreate_Adapter.CreateCreateAdapterViewHoler,
        position: Int
    ) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun addMedia(uri:Uri,isVideo: Boolean=false):Boolean{
        if(isVideo && videoExists){
            return false
        }

        if(isVideo){
            videoExists = true
        }
        items.add(MediaItem(uri,isVideo))
        notifyItemInserted(items.size -1)
        return true
    }

    fun removeMedia(position: Int){
        if(items[position].isVideo){
            videoExists = false
        }
        items.removeAt(position)
        notifyItemRemoved(position)
    }
    inner class CreateCreateAdapterViewHoler(private val binding: ItemCreateRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: MediaItem){
            if(item.isVideo){
                val thumbnail = ThumbnailUtils.createVideoThumbnail(item.uri.path!!, MediaStore.Images.Thumbnails.MINI_KIND)
                binding.ivThumnailItemCreateRv.setImageBitmap(thumbnail)
                binding.ivVideoIconItemCreateRv.visibility = View.VISIBLE
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