package info.sul_game.utils.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import info.sul_game.databinding.ImageGridFragmentBinding

class ImageGridFragment(
    private val images: List<Int>,
    private val onImageSelected: (Int) -> Unit
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = ImageGridFragmentBinding.inflate(inflater, container, false)

        val adapter = ImageGridAdapter(images) { imageRes ->
            onImageSelected(imageRes)
        }
        binding.recyclerView.layoutManager = GridLayoutManager(context, 4)
        binding.recyclerView.adapter = adapter

        return binding.root
    }
}
