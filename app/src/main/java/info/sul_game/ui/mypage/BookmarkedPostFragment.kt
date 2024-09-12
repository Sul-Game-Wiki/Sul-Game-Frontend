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
import info.sul_game.databinding.FragmentBookmarkedPostBinding

class BookmarkedPostFragment : Fragment() {

    private lateinit var binding: FragmentBookmarkedPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkedPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mockPostList = mutableListOf<BookmarkedPost>()

        mockPostList.add(BookmarkedPost("어목조동", "자연과 함께하는 술게임", "구해조"))
        mockPostList.add(BookmarkedPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임", "구해조"))

        binding.recyclerviewBookmarkedpost.adapter = BookmarkedPostAdapter(mockPostList)
        binding.recyclerviewBookmarkedpost.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewBookmarkedpost.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }
}