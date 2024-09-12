package info.sul_game.ui.recyclerview.DrinkingGame

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import info.sul_game.databinding.ItemDrinkingGameRecyclerviewBinding

class DrinkingGameAdapter(val drinkingGameList: ArrayList<DrinkingGame>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding =
            ItemDrinkingGameRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return DrinkingGameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return drinkingGameList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DrinkingGameViewHolder) {
            holder.image.setImageResource(drinkingGameList[position].image)
            holder.title.text = drinkingGameList[position].title
            holder.contents.text = drinkingGameList[position].contents
            holder.cntHeart.text = drinkingGameList[position].cntHeart.toString()
        }
    }
}