package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import info.sul_game.R
import info.sul_game.databinding.ItemOfficialListRecyclerviewBinding
import info.sul_game.databinding.ItemRelatedGameSearchRecyclerviewBinding

class OfficialGameListAdapter(val gameItemList: ArrayList<OfficialGameListItem>,
                              private val onItemClicked: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder { val binding =
            ItemOfficialListRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return OfficialGameListViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return gameItemList.size
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int
        ) {
            if (holder is OfficialGameListViewHolder) {
                holder.title.text = gameItemList[position].title
                holder.introduction.text = gameItemList[position].introduction
                Glide.with(holder.itemView.context)
                    .load(gameItemList[position].thumbnailIcon) // 서버에서 받은 이미지 URL
                    .into(holder.thumnbnailIcon)
                holder.likes.text = gameItemList[position].likes.toString()
                holder.commentCount.text = gameItemList[position].commentCount.toString()
                holder.createdDate.text = gameItemList[position].createdDate
                holder.nickName.text = gameItemList[position].nickname

                holder.itemView.setOnClickListener {
                    onItemClicked(gameItemList[position].title)
                }
            }
        }
        inner class OfficialGameListViewHolder(private val binding: ItemOfficialListRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
            var title = binding.tvTitleOfficialListItem
            var introduction = binding.tvIntroductionOfficialListItem
            var thumnbnailIcon = binding.ivThumbnailiconOfficialListItem
            var likes = binding.tvLikesOfficialListItem
            var commentCount = binding.tvCommentCountOfficialListItem
            var createdDate = binding.tvCreatedDateOfficialListItem
            var nickName=binding.tvNicknameOfficialListItem
        }
        fun updateGameList(newGameList: List<OfficialGameListItem>) {
            gameItemList.clear()
            gameItemList.addAll(newGameList)
            notifyDataSetChanged()
        }

    }
