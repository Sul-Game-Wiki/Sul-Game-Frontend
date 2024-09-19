package info.sul_game.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import info.sul_game.databinding.ItemUniversityNameRecyclerviewBinding
import info.sul_game.databinding.ItemUniversitySectionRecyclerviewBinding

class UniversitySignUpAdapter(val universitySignUpItemList: ArrayList<UniversitySignUpItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // viewType = 1 : University Name
        // viewType = 0 : University Section
        return if (viewType == 1) {
            val binding =
                ItemUniversityNameRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UniversityNameSignUpViewHolder(binding)
        } else {
            val binding =
                ItemUniversitySectionRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UniversitySectionSignUpViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return universitySignUpItemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return universitySignUpItemList[position].state
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UniversityNameSignUpViewHolder) {
            holder.university_name.text = universitySignUpItemList[position].text

            holder.itemView.setOnClickListener {
                Log.d("술겜위키", "Item Click Event")

                onItemClick?.invoke(universitySignUpItemList[position].text)
            }
        }
        else if (holder is UniversitySectionSignUpViewHolder) {
            holder.university_section.text = universitySignUpItemList[position].text
        }
    }
}