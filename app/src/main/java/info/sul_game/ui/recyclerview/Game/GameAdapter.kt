package info.sul_game.ui.recyclerview.Game

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemGameRecyclerviewBinding

class GameAdapter (val gameList: ArrayList<Game>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemGameRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is GameViewHolder) {
            holder.title.text = gameList[position].title
            holder.contents.text = gameList[position].contents
            holder.userName.text = gameList[position].userName
            holder.cntHeart.text = gameList[position].cntHeart.toString()
        }
    }
}