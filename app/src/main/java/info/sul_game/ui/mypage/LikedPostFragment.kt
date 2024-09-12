package info.sul_game.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.sul_game.R
import info.sul_game.databinding.FragmentLikedPostBinding

class LikedPostFragment : Fragment() {

    private lateinit var binding: FragmentLikedPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikedPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mockPostList = mutableListOf<LikedPost>()

        mockPostList.add(LikedPost("어목조동", "자연과 함께하는 술게임", "구해조"))
        mockPostList.add(LikedPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임", "구해조"))

        binding.recyclerviewLikedpost.adapter = LikedPostAdapter(mockPostList)
        binding.recyclerviewLikedpost.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewLikedpost.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }
}