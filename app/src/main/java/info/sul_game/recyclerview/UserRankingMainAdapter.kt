package info.sul_game.recyclerview

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.R
import info.sul_game.databinding.ItemUserRankingRecyclerviewBinding

class UserRankingMainAdapter (val userRankingMainItem: ArrayList<UserRankingMainItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemUserRankingRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return UserRankingMainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(userRankingMainItem.size < 5)
            return userRankingMainItem.size
        return 5
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is UserRankingMainViewHolder) {
            holder.image.setImageResource(userRankingMainItem[position].image)
            holder.rank.text = (position + 1).toString()
            when(userRankingMainItem[position].strongState){
                "NEW" -> {
                    holder.strong.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_strong_new, 0, 0, 0
                    )
                    holder.strong.text = ""
                }
                "UP" -> {
                    holder.strong.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_strong_up, 0, 0, 0
                    )
                    holder.strong.setTextColor(Color.parseColor("#F04849"))
                    holder.strong.text = userRankingMainItem[position].strong.toString()
                }
                "DOWN" -> {
                    holder.strong.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_strong_down, 0, 0, 0
                    )
                    holder.strong.setTextColor(Color.parseColor("#6A78BA"))
                    holder.strong.text = userRankingMainItem[position].strong.toString()
                }
                "SAME" -> {
                    holder.strong.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_strong_same, 0, 0, 0
                    )
                    holder.strong.setTextColor(Color.parseColor("#FF5733"))
                    holder.strong.text = ""
                }
            }
            holder.name.text = userRankingMainItem[position].name
            holder.point.text = "${userRankingMainItem[position].point}P"
        }
    }
}