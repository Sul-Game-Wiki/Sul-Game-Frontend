package com.example.sul_game_frontend_practice1.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.data.Post
import com.example.sul_game_frontend_practice1.data.User
import com.example.sul_game_frontend_practice1.databinding.FragmentPopularListBinding
import com.example.sul_game_frontend_practice1.databinding.FragmentPostDetailBinding
import com.example.sul_game_frontend_practice1.databinding.FragmentSearchBinding
import java.time.LocalDate
import java.time.LocalDateTime

class PostDetailFragment : Fragment() {

    private var _binding: FragmentPostDetailBinding? = null
    private val binding get() = _binding!!

    var postTitle = "실시간 순위"
    lateinit var postType : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPostDetail()
    }

    private fun initPostDetail(){
        postType = arguments?.getString("postType").toString()
        postTitle = "실시간 $postType 순위"
        binding.tvPostDetail.text = postTitle

        // Post 더미데이터 생성
        val dummyUser = User("구해조", "세종대학교", R.drawable.img_student,"user@gmail.com", LocalDate.of(2000, 1, 1))
        val dummyDateTime = LocalDateTime.of(2024, 6, 29, 0, 0)

        val dummyPostData = arrayListOf<Post>(
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", dummyUser, dummyDateTime, mutableListOf(), 5),
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", dummyUser, dummyDateTime, mutableListOf(), 9),
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", dummyUser, dummyDateTime, mutableListOf(), 8),
            Post("인기", "바니바니 게임", "하늘에서 내려온 토끼가 하는 말~", dummyUser, dummyDateTime, mutableListOf(), 11),
            Post("창작", "딸기당근수박참외 리버스", "딸기당근수박참외 리버스", dummyUser, dummyDateTime, mutableListOf(), 10),
            Post("창작", "딸기당근수박참외 리버스", "딸기당근수박참외 리버스", dummyUser, dummyDateTime, mutableListOf(), 9),
            Post("창작", "딸기당근수박참외 리버스", "딸기당근수박참외 리버스", dummyUser, dummyDateTime, mutableListOf(), 8),
            Post("인트로", "술이 고팠어", "싶었어 싶었어 마시고 싶었어, 고팠어 ~", dummyUser, dummyDateTime, mutableListOf(), 10),
            Post("인트로", "술이 고팠어", "싶었어 싶었어 마시고 싶었어, 고팠어 ~", dummyUser, dummyDateTime, mutableListOf(), 9),
            Post("인트로", "술이 고팠어", "싶었어 싶었어 마시고 싶었어, 고팠어 ~", dummyUser, dummyDateTime, mutableListOf(), 8)
        )

        // 타입별로 분류하고 정렬
        val groupedAndSortedPosts = dummyPostData.groupBy { it.type }.mapValues { entry ->
            entry.value.sortedByDescending { it.heartCnt }
        }

        val postData = groupedAndSortedPosts[postType] ?: emptyList()

        binding.rvPostDetail.adapter = PostDetailAdapter(postData)
        binding.rvPostDetail.layoutManager = LinearLayoutManager(context)
    }
}