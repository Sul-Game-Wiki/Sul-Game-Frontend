package info.sul_game.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemLatestFeedRecyclerviewBinding

class LatestFeedMainAdapter (val latestFeedMainItemList: ArrayList<LatestFeedMainItem>) :
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
            holder.feedImage.setImageResource(latestFeedMainItemList[position].feedImage)
            holder.title.text = latestFeedMainItemList[position].title
            holder.profileImage.setImageResource(latestFeedMainItemList[position].profileImage)
            holder.username.text = latestFeedMainItemList[position].username
            holder.cntHeart.text = latestFeedMainItemList[position].cntHeart.toString()
        }
    }
}