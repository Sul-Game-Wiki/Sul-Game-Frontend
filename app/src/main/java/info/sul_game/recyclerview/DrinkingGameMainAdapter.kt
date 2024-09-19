package info.sul_game.recyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import info.sul_game.databinding.ItemDrinkingGameRecyclerviewBinding

class DrinkingGameMainAdapter(val drinkingGameMainItemList: ArrayList<DrinkingGameMainItem>) :
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
        return DrinkingGameMainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        if(drinkingGameMainItemList.size < 9)
            return drinkingGameMainItemList.size
        return 9
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DrinkingGameMainViewHolder) {
            holder.image.setImageResource(drinkingGameMainItemList[position].image)
            holder.title.text = drinkingGameMainItemList[position].title
            holder.contents.text = drinkingGameMainItemList[position].contents
            holder.cntHeart.text = drinkingGameMainItemList[position].cntHeart.toString()
        }
    }
}