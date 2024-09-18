package info.sul_game.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import info.sul_game.R
import info.sul_game.databinding.ActivityWelcomeGuideBinding


class WelcomeGuideActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeGuideBinding

    private var currentIndicatorIndex = 0
    private val totalIndicators = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 초기 인디케이터 상태 설정
        binding.cidPositionWelcomeGuide.createIndicators(totalIndicators, currentIndicatorIndex)

        binding.tvSkipWelcomeGuide.setOnClickListener{
            val prefs: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
            prefs.edit().putBoolean("firstTime", false).apply()

            Log.d("술겜위키", "가이드 스킵!")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnNextWelcomeGuide.setOnClickListener{
            currentIndicatorIndex = (currentIndicatorIndex + 1) % totalIndicators
            when(currentIndicatorIndex){
                1 -> guide2()
                2 -> guide3()
                3 -> guide4()
                4 -> guide5()
                0 -> {
                    val prefs: SharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE)
                    prefs.edit().putBoolean("firstTime", false).apply()

                    Log.d("술겜위키", "가이드 끝남!")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            binding.cidPositionWelcomeGuide.animatePageSelected(currentIndicatorIndex)
        }
    }

    private fun guide2(){
        binding.tvPrompt1WelcomeGuide.text = "인기 술게임"
        binding.tvPrompt2WelcomeGuide.text = "유명하고 인기있는 술게임을 소개해요!"
        binding.ivPreviewWelcomeGuide.setImageResource(R.drawable.guide_image2)
    }

    private fun guide3(){
        binding.tvPrompt1WelcomeGuide.text = "창작 술게임"
        binding.tvPrompt2WelcomeGuide.text = "새로운 술게임을 직접 창작하고\n서로 공유해요!"
        binding.ivPreviewWelcomeGuide.setImageResource(R.drawable.guide_image3)
    }

    private fun guide4(){
        binding.tvPrompt1WelcomeGuide.text = "인트로 자랑하기"
        binding.tvPrompt2WelcomeGuide.text = "술게임 시작을 알리는 인트로를\n창작하고 공유해요!"
        binding.ivPreviewWelcomeGuide.setImageResource(R.drawable.guide_image4)
    }

    private fun guide5(){
        binding.tvPrompt1WelcomeGuide.text = "다양한 랭킹 차트"
        binding.tvPrompt2WelcomeGuide.text = "다양한 기준으로 분류된\n술겜위키만의 랭킹 차트!"
        binding.btnNextWelcomeGuide.text = "시작하기"
        binding.ivPreviewWelcomeGuide.setImageResource(0)
    }
}