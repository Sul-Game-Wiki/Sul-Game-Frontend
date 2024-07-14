package com.example.sul_game_frontend_practice1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.databinding.FragmentPopularListBinding
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntro
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntroAdapter
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntroDecoration

class PopularListFragment : Fragment() {
    private var _binding: FragmentPopularListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PopularListMain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun PopularListMain() {
        val popular_list_data = arrayListOf<GameIntro>(
            GameIntro(
                "바니바니 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "고래 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "지하철 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "두부 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "공산당 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
        )

        binding.rvPopularPopularList.adapter = GameIntroAdapter(popular_list_data)
        val ver_space_decoration = GameIntroDecoration()
        binding.rvPopularPopularList.addItemDecoration(ver_space_decoration)
        binding.rvPopularPopularList.layoutManager =
            LinearLayoutManager(context)
    }
}