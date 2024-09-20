package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import info.sul_game.R
import info.sul_game.databinding.ItemMypagePostRecyclerviewBinding
import info.sul_game.databinding.ItemSearchRecyclerviewBinding
import info.sul_game.model.Intro
import info.sul_game.ui.mypage.MyPagePostAdapter.MyPagePostHolder

class SearchAdapter(private var items: MutableList<SearchResultItem>)
    : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding = ItemSearchRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(
        holder: SearchHolder,
        position: Int
    ) {
        holder.tvTitle.text = items[position].title
        holder.tvIntroduction.text = items[position].introduction
        holder.tvLikes.text = items[position].likes.toString()
        holder.tvComments.text = items[position].views.toString()

        if(items[position].creatorInfoPrivate)
            holder.tvNickname.text = "익명"
        else
            holder.tvNickname.text = items[position].member.nickname

        holder.ivThumbnail.clipToOutline = true

        val defaultImage = R.color.light_gray
        Glide.with(holder.itemView.context)
            .load(items[position].thumbnailIcon) // 불러올 이미지 url
            .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
            .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
            .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
            .into(holder.ivThumbnail) // 이미지를 넣을 뷰
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<SearchResultItem>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    inner class SearchHolder(binding: ItemSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitleMypagePostItem
        val tvIntroduction = binding.tvIntroductionMypagePostItem
        val tvLikes = binding.tvLikesMypagePostItem
        val tvComments = binding.tvCommentsMypagePostItem
        val tvTime = binding.tvTimeMypagePostItem
        val tvNickname = binding.tvNicknameMypagePostItem
        val ivThumbnail = binding.ivThumbnailMypagePostItem
    }
}