package com.example.sul_game_frontend_practice1.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.data.Post
import com.example.sul_game_frontend_practice1.data.User
import com.example.sul_game_frontend_practice1.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSearchFragment(){
        // Post 더미데이터 생성
        val dummyPostData = arrayListOf<Post>(
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", User("구해조"), mutableListOf(), 5),
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", User("구해조"), mutableListOf(), 9),
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", User("구해조"), mutableListOf(), 8),
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", User("구해조"), mutableListOf(), 11),
            Post("창작", "딸기당근수박참외 리버스", "딸기당근수박참외 리버스", User("구해조"), mutableListOf(), 10),
            Post("창작", "딸기당근수박참외 리버스", "딸기당근수박참외 리버스", User("구해조"), mutableListOf(), 9),
            Post("창작", "딸기당근수박참외 리버스", "딸기당근수박참외 리버스", User("구해조"), mutableListOf(), 8),
            Post("인트로", "술이 고팠어", "싶었어 싶었어 마시고 싶었어, 고팠어 ~", User("구해조"), mutableListOf(), 10),
            Post("인트로", "술이 고팠어", "싶었어 싶었어 마시고 싶었어, 고팠어 ~", User("구해조"), mutableListOf(), 9),
            Post("인트로", "술이 고팠어", "싶었어 싶었어 마시고 싶었어, 고팠어 ~", User("구해조"), mutableListOf(), 8)
        )

        // 타입별로 분류하고 정렬
        val groupedAndSortedPosts = dummyPostData.groupBy { it.type }.mapValues { entry ->
            entry.value.sortedByDescending { it.heartCnt }
        }

        // 타입별로 변수에 할당
        val introPosts = groupedAndSortedPosts["인트로"] ?: emptyList()
        val popularPosts = groupedAndSortedPosts["인기"] ?: emptyList()
        val creativePosts = groupedAndSortedPosts["창작"] ?: emptyList()

        binding.rv1TrendSearch.adapter = TrendAdapter(popularPosts)
        binding.rv1TrendSearch.layoutManager = LinearLayoutManager(context)

        binding.rv2TrendSearch.adapter = TrendAdapter(creativePosts)
        binding.rv2TrendSearch.layoutManager = LinearLayoutManager(context)

        binding.rv3TrendSearch.adapter = TrendAdapter(introPosts)
        binding.rv3TrendSearch.layoutManager = LinearLayoutManager(context)


    }
}