package com.example.sul_game_frontend_practice1

import android.content.Intent
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.sul_game_frontend_practice1.databinding.ActivityNicknameBinding
import com.example.sul_game_frontend_practice1.databinding.ActivityStartBinding

class NicknameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNicknameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCloseNickname.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        binding.etNameNickname.addTextChangedListener {
            binding.tvCountLengthNickname.text =
                binding.etNameNickname.text.length.toString() + "/10"

            val text = binding.etNameNickname.text.toString()

            // 사용 가능 닉네임인지 확인
            if (text.isEmpty()) {
                binding.tvHint1Nickname.visibility = View.VISIBLE
                binding.tvHint2Nickname.visibility = View.GONE
                binding.tvHint3Nickname.visibility = View.GONE
            } else if (availableName(text)) {
                binding.tvHint1Nickname.visibility = View.GONE
                binding.tvHint2Nickname.visibility = View.VISIBLE
                binding.tvHint3Nickname.visibility = View.GONE
            } else {
                binding.tvHint1Nickname.visibility = View.GONE
                binding.tvHint2Nickname.visibility = View.GONE
                binding.tvHint3Nickname.visibility = View.VISIBLE
            }

            buttonClickable()
        }

        binding.btnNextNickname.setOnClickListener{
            startActivity(Intent(this, BirthActivity::class.java))
            finish()
        }
    }

    /**
     * TODO : 서버랑 합체해야됨
     * 사용 가능 닉네임인지 확인
     */
    private fun availableName(name: String): Boolean {
        return true
    }

    /**
     * Button Clickable 설정
     * 입력한 닉네임이 제대로 쓰여졌으면 클릭 허용
     */
    private fun buttonClickable() {
        if (binding.tvHint2Nickname.visibility == View.VISIBLE) {
            binding.btnNextNickname.isEnabled = true
            binding.btnNextNickname.backgroundTintList = ContextCompat.getColorStateList(this, R.color.main_color)
        } else {
            binding.btnNextNickname.isEnabled = false
            binding.btnNextNickname.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_gray)
        }
    }
}