package com.example.sul_game_frontend_practice1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guhaejo.main.game.Game
import com.example.guhaejo.main.game.GameAdapter
import com.example.guhaejo.main.game.GameDecoration
import com.example.guhaejo.main.intro.Intro
import com.example.guhaejo.main.intro.IntroAdapter
import com.example.guhaejo.main.intro.IntroDecoration
import com.example.sul_game_frontend_practice1.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PopularMain()
        CreationMain()
        IntroMain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun PopularMain(){
        val popular_data = arrayListOf<Game>(
            Game("바니바니 게임",  14),
            Game("고래 게임",  10),
            Game("지하철 게임",  8),
            Game("두부 게임",  5),
            Game("공산당 게임",  2),
        )

        binding.rvPopularHome.adapter = GameAdapter(popular_data)
        val ver_space_decoration = GameDecoration()
        binding.rvPopularHome.addItemDecoration(ver_space_decoration)
        binding.rvPopularHome.layoutManager =
            LinearLayoutManager(context)
    }

    private fun CreationMain(){
        val creation_data = arrayListOf<Game>(
            Game("바니바니 삽",  15),
            Game("딸기당근수박참외 리버스",  13),
            Game("딸기당근수박참외 찍고",  8),
            Game("두부 딸기",  7),
            Game("민주당",  2),
        )

        binding.rvCreateHome.adapter = GameAdapter(creation_data)
        val ver_space_decoration = GameDecoration()
        binding.rvCreateHome.addItemDecoration(ver_space_decoration)
        binding.rvCreateHome.layoutManager =
            LinearLayoutManager(context)
    }

    private fun IntroMain(){
        val intro_data = arrayListOf<Intro>(
            Intro("술이 고팠어", "싶었어 싶었어 마시고 싶었어 ~ 고팠어 고팠어 술이 고팠어 ~", 21),
            Intro("뭐할래", "바보야 바보야 뭐할래 뭐할래 뭐할래 ~ 뭐할래 뭐할래 뭐할래 ~", 15),
            Intro("뭐할래", "바보야 바보야 뭐할래 뭐할래 뭐할래 ~ 뭐할래 뭐할래 뭐할래 ~", 14),
            Intro("뭐할래", "바보야 바보야 뭐할래 뭐할래 뭐할래 ~ 뭐할래 뭐할래 뭐할래 ~", 11),
            Intro("뭐할래", "바보야 바보야 뭐할래 뭐할래 뭐할래 ~ 뭐할래 뭐할래 뭐할래 ~", 8),
            Intro("뭐할래", "바보야 바보야 뭐할래 뭐할래 뭐할래 ~ 뭐할래 뭐할래 뭐할래 ~", 7),
            Intro("뭐할래", "바보야 바보야 뭐할래 뭐할래 뭐할래 ~ 뭐할래 뭐할래 뭐할래 ~", 6),
        )

        binding.rvIntroHome.adapter = IntroAdapter(intro_data)
        val ver_space_decoration = IntroDecoration()
        binding.rvIntroHome.addItemDecoration(ver_space_decoration)
        binding.rvIntroHome.layoutManager =
            LinearLayoutManager(context)
    }

    /**
     * Button Click Event
     */
    private fun ButtonClick(){
        // 인기 '...' 클릭 이벤트
        binding.btnPopularDetailHome.setOnClickListener{
            // Fragment 전환을 처리합니다.
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, PopularListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // '인기 게임 더보기' 클릭 이벤트
        binding.btnPopularHome.setOnClickListener{
            // Fragment 전환을 처리합니다.
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, PopularListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // 창작 '...' 클릭 이벤트
        binding.btnCreateDetailHome.setOnClickListener{
            // Fragment 전환을 처리합니다.
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, CreateListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // '창작 게임 더보기' 클릭 이벤트
        binding.btnCreateHome.setOnClickListener{
            // Fragment 전환을 처리합니다.
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, PopularListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        // 인트로 '...' 클릭 이벤트
        binding.btnIntroDetailHome.setOnClickListener{
            // Fragment 전환을 처리합니다.
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, IntroListFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}