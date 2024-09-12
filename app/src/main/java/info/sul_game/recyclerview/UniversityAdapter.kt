package info.sul_game.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import info.sul_game.databinding.ItemUniversityNameRecyclerviewBinding
import info.sul_game.databinding.ItemUniversitySectionRecyclerviewBinding

class UniversityAdapter(val universityList: ArrayList<University>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // viewType = 1 : University Name
        // viewType = 0 : University Section
        return if (viewType == 1) {
            val binding =
                ItemUniversityNameRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UniversityNameViewHolder(binding)
        } else {
            val binding =
                ItemUniversitySectionRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            UniversitySectionViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return universityList.size
    }

    override fun getItemViewType(position: Int): Int {
        return universityList[position].state
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UniversityNameViewHolder) {
            holder.university_name.text = universityList[position].text

            holder.itemView.setOnClickListener {
                Log.d("술겜위키", "Item Click Event")

                onItemClick?.invoke(universityList[position].text)
            }
        }
        else if (holder is UniversitySectionViewHolder) {
            holder.university_section.text = universityList[position].text
        }
    }
}