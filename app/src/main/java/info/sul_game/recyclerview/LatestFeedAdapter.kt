package info.sul_game.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemIntroRecyclerviewBinding
import info.sul_game.databinding.ItemLatestFeedRecyclerviewBinding

class LatestFeedAdapter (val latestFeedItemList: ArrayList<LatestFeedItem>) :
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
        return LatestFeedViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return latestFeedItemList.size
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is LatestFeedViewHolder) {
            holder.feedImage.setImageResource(latestFeedItemList[position].feedImage)
            holder.title.text = latestFeedItemList[position].title
            holder.profileImage.setImageResource(latestFeedItemList[position].profileImage)
            holder.username.text = latestFeedItemList[position].username
            holder.cntHeart.text = latestFeedItemList[position].cntHeart.toString()
        }
    }
}