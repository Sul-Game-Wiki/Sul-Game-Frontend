package com.example.sul_game_frontend_practice1.search

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.FragmentInfoBinding
import com.example.sul_game_frontend_practice1.databinding.FragmentSearchBinding
import com.example.sul_game_frontend_practice1.editprofile.EditProfileActivity
import com.example.sul_game_frontend_practice1.editprofile.EditProfileImageActivity

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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}