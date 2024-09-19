package info.sul_game.utils.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ImagePagerAdapter(
    fragmentActivity: FragmentActivity,
    private val imagesList: List<List<Int>>,
    private val onImageSelected: (Int) -> Unit
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = imagesList.size

    override fun createFragment(position: Int): Fragment {
        return ImageGridFragment(imagesList[position], onImageSelected)
    }
}
