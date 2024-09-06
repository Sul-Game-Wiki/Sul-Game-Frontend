package com.example.sul_game_frontend_practice1.create

import android.content.ContentProvider
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

import com.example.sul_game_frontend_practice1.databinding.ActivityCreateBinding
import com.example.sul_game_frontend_practice1.databinding.FragmentCreateCreateBinding
import com.example.sul_game_frontend_practice1.intro.IntroCreateFragment
import com.google.android.material.chip.Chip

class Create : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pagerAdapter = CreateFragmentPagerAdapter(this)
        binding.vpCreate.adapter = pagerAdapter
        binding.btnChangeCreate.setOnClickListener {
            val currentItem = binding.vpCreate.currentItem
            binding.vpCreate.currentItem = if (currentItem == 0) 1 else 0
        }


    }

    class CreateFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        override fun getItemCount(): Int = 2 // 0번과 1번, 두 개의 프래그먼트

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CreateCreateFragment()
                1 -> IntroCreateFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }

        }


}}