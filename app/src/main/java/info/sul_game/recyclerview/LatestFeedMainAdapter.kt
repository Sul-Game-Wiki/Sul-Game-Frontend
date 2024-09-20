package info.sul_game.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import info.sul_game.R
import info.sul_game.activity.MainActivity
import info.sul_game.databinding.ItemLatestFeedRecyclerviewBinding

class LatestFeedMainAdapter (val context: Context, val latestFeedMainItemList: ArrayList<LatestFeedMainItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemLatestFeedRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LatestFeedMainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(latestFeedMainItemList.size < 9)
            return latestFeedMainItemList.size
        return 9
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is LatestFeedMainViewHolder) {
            Glide.with(context)
                .load(latestFeedMainItemList[position].feedImage) // 불러올 이미지 url
                .circleCrop() // 동그랗게 자르기
                .into(holder.feedImage) // 이미지를 넣을 뷰
            holder.title.text = latestFeedMainItemList[position].title
            Log.d("술겜위키", "image url : ${latestFeedMainItemList[position].profileImage}")
            Glide.with(context)
                .load(latestFeedMainItemList[position].profileImage) // 불러올 이미지 url
                .circleCrop() // 동그랗게 자르기
                .error(R.drawable.ic_launcher_background)
                .into(holder.profileImage) // 이미지를 넣을 뷰
            holder.username.text = latestFeedMainItemList[position].username
            holder.cntHeart.text = latestFeedMainItemList[position].cntHeart.toString()
        }
    }
}