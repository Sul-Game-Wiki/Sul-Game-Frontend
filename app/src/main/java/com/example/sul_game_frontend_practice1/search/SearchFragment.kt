package com.example.sul_game_frontend_practice1.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.PopularListFragment
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.data.Post
import com.example.sul_game_frontend_practice1.data.User
import com.example.sul_game_frontend_practice1.databinding.FragmentSearchBinding
import java.time.LocalDate
import java.time.LocalDateTime

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

        buttonClicked()

    }

    private fun buttonClicked(){
        val bundle = Bundle()

        val postDetailFragment = PostDetailFragment()

        binding.btn1DetailSearch.setOnClickListener {
            bundle.putString("postType", "인기")
            postDetailFragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, postDetailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.btn2DetailSearch.setOnClickListener {
            bundle.putString("postType", "창작")
            postDetailFragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, postDetailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.btn3DetailSearch.setOnClickListener {
            bundle.putString("postType", "인트로")
            postDetailFragment.arguments = bundle

            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, postDetailFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}