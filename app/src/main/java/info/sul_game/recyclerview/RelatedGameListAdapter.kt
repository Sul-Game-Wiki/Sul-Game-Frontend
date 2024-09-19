package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemRelatedGameSearchRecyclerviewBinding

class RelatedGameListAdapter(
    private val gameItemList: ArrayList<RelatedGameListItem>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<RelatedGameListAdapter.GameListViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameListViewHolder {
        val binding = ItemRelatedGameSearchRecyclerviewBinding.inflate(
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
        holder: GameListViewHolder,
        position: Int
    ) {
        val gameItem = gameItemList[position]

        // Null-safe title and introduction assignment
        holder.title.text = gameItem.title ?: "No Title"
        holder.introduction.text = gameItem.introduction ?: "No Introduction"

        // Handle item click
        holder.itemView.setOnClickListener {
            gameItem.title?.let { title ->
                onItemClicked(title)
            }
        }
    }

    // ViewHolder for the RecyclerView
    inner class GameListViewHolder(val binding: ItemRelatedGameSearchRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvTitleGameItem
        val introduction = binding.tvIntroductionGameItem
    }

    // Method to update the game list in the adapter
    fun updateGameList(newGameList: List<RelatedGameListItem>) {
        gameItemList.clear()
        gameItemList.addAll(newGameList)
        notifyDataSetChanged()
    }
}
