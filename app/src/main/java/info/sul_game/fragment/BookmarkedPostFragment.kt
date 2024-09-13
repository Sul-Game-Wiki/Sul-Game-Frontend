package info.sul_game.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
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

        val mockPostList = mutableListOf<MyPagePostItem>()

        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))

        binding.recyclerviewBookmarkedpost.adapter = MyPagePostAdapter(mockPostList)
        binding.recyclerviewBookmarkedpost.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewBookmarkedpost.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }
}