package info.sul_game.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemIntroRecyclerviewBinding

class IntroMainAdapter (val introMainItemList: ArrayList<IntroMainItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemIntroRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return IntroMainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is IntroMainViewHolder) {
            holder.title.text = introMainItemList[position].title
            holder.contents.text = introMainItemList[position].contents
            holder.userName.text = introMainItemList[position].userName
            holder.cntHeart.text = introMainItemList[position].cntHeart.toString()
        }
    }
}