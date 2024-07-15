package com.example.sul_game_frontend_practice1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.databinding.FragmentCreateListBinding
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntro
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntroAdapter
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntroDecoration

class CreateListFragment : Fragment() {
    private var _binding: FragmentCreateListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CreateListMain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun CreateListMain() {
        val create_list_data = arrayListOf<GameIntro>(
            GameIntro(
                "바니바니 삽 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "딸기당근수박참외 리버스 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "딸기당근수박참외 찍고 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "두부 딸기 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "민주당 게임",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
        )

        binding.rvCreateCreateList.adapter = GameIntroAdapter(create_list_data)
        val ver_space_decoration = GameIntroDecoration()
        binding.rvCreateCreateList.addItemDecoration(ver_space_decoration)
        binding.rvCreateCreateList.layoutManager =
            LinearLayoutManager(context)
    }
}