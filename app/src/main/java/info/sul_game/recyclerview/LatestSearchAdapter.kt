package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.databinding.ItemRecentSearchRecyclerviewBinding

class LatestSearchAdapter(
    private var items : MutableList<String>,
    private val onItemClick : (query: String) -> Unit): RecyclerView.Adapter<LatestSearchAdapter.RecentSearchHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchHolder {
        val binding = ItemRecentSearchRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecentSearchHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(
        holder: RecentSearchHolder,
        position: Int
    ) {
        holder.textView.text = items[position]

        holder.itemView.setOnClickListener {
            onItemClick(items[position])
        }

        holder.button.setOnClickListener {
            onItemClick(items[position])
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<String>) {
        items = newItems.toMutableList()
        notifyDataSetChanged()
    }

    inner class RecentSearchHolder(binding: ItemRecentSearchRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView = binding.tvRecentSearchItem
        val button = binding.ibRecentSearchItem
    }
}