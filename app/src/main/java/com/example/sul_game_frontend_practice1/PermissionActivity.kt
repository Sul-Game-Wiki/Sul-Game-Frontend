package com.example.sul_game_frontend_practice1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sul_game_frontend_practice1.databinding.ActivityPermissionBinding
import com.example.sul_game_frontend_practice1.databinding.ActivitySchoolBinding

class PermissionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionBinding
    private lateinit var checkBoxList: List<CheckBox>
    private var isAllCheckBoxChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCloseBirth.setOnClickListener{
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        checkBoxList = listOf(
            binding.cbPermission2Permission,
            binding.cbPermission3Permission,
            binding.cbPermission4Permission,
            binding.cbPermission5Permission,
            binding.cbPermission6Permission,
            binding.cbPermission7Permission,
        )

        // '모두 동의합니다' 체크박스 클릭 이벤트
        binding.cbPermission1Permission.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                isAllCheckBoxChecked = true
                buttonClickable(true)
                checkBoxList.forEach { checkBox ->
                    checkBox.isChecked = true
                }
            } else {
                if (isAllCheckBoxChecked) {
                    isAllCheckBoxChecked = false
                    buttonClickable(false)
                    checkBoxList.forEach { checkBox ->
                        checkBox.isChecked = false
                    }
                }
            }
        }

        // 개별 체크박스 클릭 이벤트
        checkBoxList.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, _ ->
                if (!checkBox.isChecked) {
                    isAllCheckBoxChecked = false
                    binding.cbPermission1Permission.isChecked = false
                    buttonClickable(false)
                } else if (checkBoxList.all { it.isChecked }) {
                    isAllCheckBoxChecked = true
                    binding.cbPermission1Permission.isChecked = true
                    buttonClickable(true)
                }
            }
        }

        binding.btnNextBirth.setOnClickListener{
            startActivity(Intent(this, ConfirmationActivity::class.java))
            finish()
        }
    }

    /**
     * Button Clickable 설정
     * 입력한 닉네임이 제대로 쓰여졌으면 클릭 허용
     */
    private fun buttonClickable(state: Boolean) {
        if (state) {
            binding.btnNextBirth.isEnabled = true
            binding.btnNextBirth.backgroundTintList = ContextCompat.getColorStateList(this, R.color.main_color)
        } else {
            binding.btnNextBirth.isEnabled = false
            binding.btnNextBirth.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_gray)
        }
    }
}