package com.example.sul_game_frontend_practice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
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