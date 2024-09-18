package info.sul_game.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemIntroRecyclerviewBinding

class IntroAdapter (val introItemList: ArrayList<IntroItem>) :
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
        return IntroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is IntroViewHolder) {
            holder.title.text = introItemList[position].title
            holder.contents.text = introItemList[position].contents
            holder.userName.text = introItemList[position].userName
            holder.cntHeart.text = introItemList[position].cntHeart.toString()
        }
    }
}