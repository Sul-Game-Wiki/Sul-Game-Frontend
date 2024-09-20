package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import info.sul_game.R
import info.sul_game.databinding.ItemChartRecyclerviewBinding


class LiveChartMainAdapter(val context: Context, val liveCharList: ArrayList<LiveChartMainItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemChartRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LiveChartMainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(liveCharList.size < 9)
            return liveCharList.size
        return 9
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is LiveChartMainViewHolder) {
            Glide.with(context)
                .load(liveCharList[position].image) // 불러올 이미지 url
                .circleCrop() // 동그랗게 자르기
                .error(R.drawable.ic_launcher_background)
                .into(holder.image) // 이미지를 넣을 뷰
            holder.rank.text = (position + 1).toString()
            holder.title.text = liveCharList[position].title
            holder.contents.text = liveCharList[position].contents
            holder.cntHeart.text = liveCharList[position].cntHeart.toString()
        }
    }
}