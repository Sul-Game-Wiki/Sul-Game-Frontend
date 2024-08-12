package com.example.sul_game_frontend_practice1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
import com.example.sul_game_frontend_practice1.databinding.ActivitySearchBinding
import com.example.sul_game_frontend_practice1.search.CustomSearchView

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchviewSearch.setOnBackListener {
            finish()
        }
    }

    private fun setOnQueryTextListener(){
        binding.searchviewSearch.setOnQueryTextListener(object : CustomSearchView.OnQueryTextListener{
            // 검색 버튼 입력 시 호출
            override fun onQueryTextChange(newText: String): Boolean {
                TODO("Not yet implemented")
            }

            // 텍스트 입력 및 수정 시 호출
            override fun onQueryTextSubmit(query: String): Boolean {
                TODO("Not yet implemented")
            }

        } )
    }
}