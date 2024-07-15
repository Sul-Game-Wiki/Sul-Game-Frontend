package com.example.sul_game_frontend_practice1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sul_game_frontend_practice1.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ibtnGoogleLogin.setOnClickListener{
            login()
        }

        binding.ibtnKakaoLogin.setOnClickListener{
            login()
        }
    }

    private fun login(){
        Toast.makeText(this, "회원가입을 해주세요", Toast.LENGTH_SHORT).show();
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
        finish()
    }
}