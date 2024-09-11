package info.sul_game.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemMypostRecyclerviewBinding

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