package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemOfficialGameRecyclerviewBinding

class GameListAdapter (val gameItemList: ArrayList<GameListItem>,
                       private val onItemClicked: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder { val binding =
            ItemOfficialGameRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return GameListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return gameItemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is GameListViewHolder) {
            holder.title.text = gameItemList[position].title
            holder.introduction.text = gameItemList[position].introduction

            holder.itemView.setOnClickListener {
                onItemClicked(gameItemList[position].title)
            }
        }
    }
    inner class GameListViewHolder(private val binding: ItemOfficialGameRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){
        var title = binding.tvTitleGameItem
        var introduction = binding.tvIntroductionGameItem
}
    fun updateGameList(newGameList: List<GameListItem>) {
        gameItemList.clear()
        gameItemList.addAll(newGameList)
        notifyDataSetChanged()
    }

    }