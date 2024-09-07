package com.example.sul_game_frontend_practice1.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.FragmentCreateCreateBinding
import com.example.sul_game_frontend_practice1.databinding.FragmentIntroCreateBinding

class IntroCreateFragment : Fragment() {
    private lateinit var binding: FragmentIntroCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}