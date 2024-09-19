package info.sul_game.ui.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import info.sul_game.R
import info.sul_game.data.source.remote.BasePost
import info.sul_game.databinding.ItemMypagePostRecyclerviewBinding

class MyPagePostAdapter(private var item : List<BasePost>) : RecyclerView.Adapter<MyPagePostAdapter.MyPagePostHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPagePostHolder {
        val binding = ItemMypagePostRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPagePostHolder(binding)
    }

    override fun getItemCount(): Int {
        return item.count()
    }

    override fun onBindViewHolder(
        holder: MyPagePostHolder,
        position: Int
    ) {
        holder.tvTitle.text = item[position].title
        holder.tvIntroduction.text = item[position].introduction
        holder.tvLikes.text = item[position].likes.toString()
        holder.tvComments.text = item[position].views.toString()
        holder.tvTime.text = item[position].updatedDate.toString()

        if(item[position].creatorInfoPrivate)
            holder.tvNickname.text = "익명"
        else
            holder.tvNickname.text = item[position].member.nickname

        holder.ivThumbnail.clipToOutline = true

        val defaultImage = R.color.light_gray
        Glide.with(holder.itemView.context)
            .load(item[position].thumbnailIcon) // 불러올 이미지 url
            .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
            .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
            .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
            .into(holder.ivThumbnail) // 이미지를 넣을 뷰
    }

    inner class MyPagePostHolder(binding: ItemMypagePostRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvTitle = binding.tvTitleMypagePostItem
        val tvIntroduction = binding.tvIntroductionMypagePostItem
        val tvLikes = binding.tvLikesMypagePostItem
        val tvComments = binding.tvCommentsMypagePostItem
        val tvTime = binding.tvTimeMypagePostItem
        val tvNickname = binding.tvNicknameMypagePostItem
        val ivThumbnail = binding.ivThumbnailMypagePostItem
    }
}