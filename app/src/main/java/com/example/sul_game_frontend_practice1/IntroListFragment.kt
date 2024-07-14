package com.example.sul_game_frontend_practice1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.databinding.FragmentIntroListBinding
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntro
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntroAdapter
import com.example.sul_game_frontend_practice1.main.game_intro.GameIntroDecoration


class IntroListFragment : Fragment() {
    private var _binding: FragmentIntroListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIntroListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        IntroListMain()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun IntroListMain() {
        val intro_list_data = arrayListOf<GameIntro>(
            GameIntro(
                "뭐할래",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "뭐할래",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "뭐할래",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "뭐할래",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
            GameIntro(
                "뭐할래",
                "06/29 00:00",
                R.drawable.ic_launcher_background,
                "세종대학교",
                "구해조"
            ),
        )

        binding.rvIntroIntroList.adapter = GameIntroAdapter(intro_list_data)
        val ver_space_decoration = GameIntroDecoration()
        binding.rvIntroIntroList.addItemDecoration(ver_space_decoration)
        binding.rvIntroIntroList.layoutManager =
            LinearLayoutManager(context)
    }
}