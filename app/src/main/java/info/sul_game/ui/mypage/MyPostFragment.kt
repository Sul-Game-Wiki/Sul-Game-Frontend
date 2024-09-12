package info.sul_game.ui.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.sul_game.databinding.FragmentMyPostBinding

class MyPostFragment : Fragment() {

    private lateinit var binding: FragmentMyPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myPostList = mutableListOf<MyPost>()

        myPostList.add(MyPost("어목조동", "자연과 함께하는 술게임"))
        myPostList.add(MyPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임"))

        binding.recyclerviewMypost.adapter = MyPostAdapter(myPostList)
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewMypost.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }
}