package info.sul_game.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import info.sul_game.R
import info.sul_game.databinding.ActivityMypageBinding
import info.sul_game.utils.TokenUtil
import info.sul_game.viewmodel.MemberViewModel
import kotlin.math.abs

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding
    private val memberViewModel: MemberViewModel by viewModels()

    private val TAG = "MYPAGE"

    // 각 등급별 기준 경험치
    private val baseExp = listOf(0, 500, 2000, 5000)
    private val baseExpLevelImage = listOf(R.drawable.ic_explevel_k, R.drawable.ic_explevel_w, R.drawable.ic_explevel_g, R.drawable.ic_explevel_s)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMypageBinding.inflate(layoutInflater)

        initUiEvent()
        updateMyPageUiWithData()

        setContentView(binding.root)
    }

    private fun initUiEvent() {
        binding.btnMypostMypage.setOnClickListener {
            val intent = Intent(this@MyPageActivity, MyPostsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.btnBookmarkMypage.setOnClickListener {
            val intent = Intent(this@MyPageActivity, BookmarkedPostsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.btnLikedpostMypage.setOnClickListener {
            val intent = Intent(this@MyPageActivity, LikedPostsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.btnProfileSettingMypage.setOnClickListener {
            val intent = Intent(this@MyPageActivity, EditAccountActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        binding.btnRefreshMypage.setOnClickListener {
            updateMyPageUiWithData()
        }
    }

    private fun updateMyPageUiWithData() {
        val accessToken = TokenUtil().getAccessToken(this@MyPageActivity)

        // ViewModel을 사용하여 프로필 정보 요청
        accessToken?.let {
            memberViewModel.getMemberProfile("Bearer $accessToken")
            Log.d(TAG, "updateMyPageUiWithData: fetchMemberProfile ($accessToken)")

            memberViewModel.memberResponse.observe(this) { memberResponse ->
                if (memberResponse != null) {
                    Log.d(TAG, "$memberResponse")

                    binding.tvUsernameMypage.text = memberResponse.member.nickname
                    binding.tvUniversityMypage.text = memberResponse.member.university

                    binding.tvExpRankMypage.text = memberResponse.exp.toString() + "위" // 현재 순위

                    // 순위 변동
                    if (memberResponse.exp >= 0)
                        binding.tvRankChangeMypage.text = abs(memberResponse.expRank).toString() + "위 상승"
                    else
                        binding.tvRankChangeMypage.text = abs(memberResponse.expRank).toString() + "위 하락"

                    binding.tvExpRankPercentileMypage.text = memberResponse.expRankPercentile.toString() + "%" // 현재 백분위

                    binding.tvTotalExpMypage.text = memberResponse.exp.toString() // 총 경험치

                    // 경험치 등급
                    binding.tvExpLevelMypage.text = getExpLevel(getCurrentGrade(memberResponse.exp))
                    binding.tvMinExpMypage.text = getExpLevel(getCurrentGrade(memberResponse.exp))
                    binding.tvMaxExpMypage.text = getNextExpLevel(getCurrentGrade(memberResponse.exp))

                    // 다음 등급까지 남은 포인트
                    binding.tvNextLevelExpMypage.text = "${memberResponse.nextLevelExp} 포인트"

                    // 프로그레스바 계산
                    binding.progressbarExpMypage.min = 0
                    binding.progressbarExpMypage.max = 1000
                    binding.progressbarExpMypage.progress = (((memberResponse.exp - baseExp[getCurrentGrade(memberResponse.exp)]).toDouble() / ((baseExp[getNextGrade(memberResponse.exp)]) - baseExp[getCurrentGrade(memberResponse.exp)]).toDouble()) * 1000).toInt()

                    // 엠블렘 이미지 업데이트
                    binding.ivEmblemMypage.setImageResource(baseExpLevelImage[getCurrentGrade(memberResponse.exp)])
                    binding.ivEmblem2Mypage.setImageResource(baseExpLevelImage[getCurrentGrade(memberResponse.exp)])
                    binding.ivNextEmblemMypage.setImageResource(baseExpLevelImage[getNextGrade(memberResponse.exp)])

                } else {
                    Log.e(TAG,"updateMyPageUiWithData: memberRequest 데이터가 존재하지 않음")
                }
            }
        }

    }

    private fun getCurrentGrade(exp : Int) : Int {
        val currentGrade = when {
            exp >= 5000 -> 3
            exp in 2000..4999 -> 2
            exp in 500..1999 -> 1
            else -> 0
        }

        return currentGrade
    }

    private fun getNextGrade(exp : Int) : Int {
        val currentGrade = when {
            exp >= 5000 -> 3
            exp in 2000..4999 -> 2
            exp in 500..1999 -> 1
            else -> 0
        }

        return if(currentGrade<3) currentGrade+1 else currentGrade
    }

    private fun getExpLevel(grade: Int) : String {
        val grades = listOf("K (Ki)", "W (Wi)", "G (Game)", "S (Sul)")

        return grades[grade]
    }

    private fun getNextExpLevel(grade: Int) : String {
        val grades = listOf("K (Ki)", "W (Wi)", "G (Game)", "S (Sul)")

        return if(grade<3) grades[grade+1] else grades[3]

    }


}