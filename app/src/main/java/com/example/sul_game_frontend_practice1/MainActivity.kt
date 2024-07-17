package com.example.sul_game_frontend_practice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
import com.example.sul_game_frontend_practice1.search.CustomSearchView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnNavigationItemSelectedListener(navListener)

        // 알림 버튼 클릭 이벤트
        binding.btnAlarm.setOnClickListener {
            replaceFragment(AlarmFragment())
            binding.btnAlarm.setImageResource(R.drawable.ic_notification_on)

            // 바텀네비게이션의 아이템 off로 전환
            val menu = binding.bottomNavigation.menu
            for (i in 0 until menu.size()) {
                val menuItem = menu.getItem(i)
                menuItem.isCheckable = false
            }

        }

        // SearchView 쿼리 설정
        binding.searchView.setOnQueryTextListener(object : CustomSearchView.OnQueryTextListener {
            // 검색어가 변경될 때의 동작
            override fun onQueryTextChange(newText: String): Boolean {
                // 예: 필터링, 자동 완성 등
                return true
            }

            // 검색어가 제출될 때의 동작을 여기에 정의합니다.
            override fun onQueryTextSubmit(query: String): Boolean {
                // 예: 검색 실행
                return true
            }
        })

        // Default fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).commit()
        }
    }

    // 바텀네비게이션 아이템 선택 리스너
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment = when (item.itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_plus -> PlusFragment()
            R.id.nav_info -> InfoFragment()
            else -> HomeFragment()
        }

        // 바텀네비게이션의 아이템 on으로 전환
        val menu = binding.bottomNavigation.menu
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            menuItem.isCheckable = true
        }
        binding.btnAlarm.setImageResource(R.drawable.ic_notification_off)

        replaceFragment(selectedFragment)

        true
    }

    /**
     * Fragment 전환 함수
     */
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}