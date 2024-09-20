package info.sul_game.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import info.sul_game.R
import info.sul_game.config.RetrofitClient
import info.sul_game.databinding.ActivityMypageBinding
import info.sul_game.model.MemberResponse
import info.sul_game.utils.TokenUtil
import info.sul_game.utils.view.ProfileImageModalDialog
import info.sul_game.viewmodel.MemberViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class MyPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding
    private val memberViewModel: MemberViewModel by viewModels()

    private val TAG = "MYPAGE"
    private var isNotificationEnabled = true

    // 각 등급별 기준 경험치
    private val baseExp = listOf(0, 500, 2000, 5000)
    private val baseExpLevelImage = listOf(R.drawable.ic_explevel_k, R.drawable.ic_explevel_w, R.drawable.ic_explevel_g, R.drawable.ic_explevel_s)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMypageBinding.inflate(layoutInflater)

        initUiEvent()
        observeViewModel()
        updateMyPageUiWithData()
        updateNotificationButtonUI(isNotificationEnabled)

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

        binding.btnRefreshMypage.setOnClickListener {
            updateMyPageUiWithData()
        }

        binding.btnEditProfileMypage.setOnClickListener {
            val dialog = ProfileImageModalDialog()
            dialog.show(supportFragmentManager, dialog.tag)

        }

        binding.btnCloseMypage.setOnClickListener {
            finish()
        }

        binding.btnQuestionMypage.setOnClickListener {
            val intent = Intent(this@MyPageActivity, IntroduceGradeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }

    private val rankChangeColors = listOf("#F04849", "#6A78BA", "#747474")

    private fun observeViewModel() {
        // ViewModel을 사용하여 프로필 정보 요청
        memberViewModel.memberResponse.observe(this) { memberResponse ->
            memberResponse?.let {
                Log.d(TAG, "$it")

                binding.tvUsernameMypage.text = it.member.nickname
                binding.tvEmailMypage.text = it.member.email
                binding.tvUniversityMypage.text = it.member.university

                binding.tvCurrentRankMypage.text = "${it.expRank}위" // 현재 순위

                // 순위 변동
                binding.tvRankChangeMypage.text = abs(it.expRank).toString()

                if (it.rankChange > 0) {
                    binding.tvRankChangeMypage.setTextColor(
                        Color.parseColor(
                            rankChangeColors[0]
                        )
                    )
                    binding.ivRankMypage.setImageResource(R.drawable.ic_rank_up)

                } else if (it.rankChange < 0) {
                    binding.tvRankChangeMypage.setTextColor(
                        Color.parseColor(
                            rankChangeColors[1]
                        )
                    )
                    binding.ivRankMypage.setImageResource(R.drawable.ic_rank_down)
                } else {
                    binding.tvRankChangeMypage.setTextColor(
                        Color.parseColor(
                            rankChangeColors[2]
                        )
                    )
                    binding.ivRankMypage.setImageResource(R.drawable.ic_rank_equal)
                }

                binding.tvCurrentRankPercentileMypage.text =
                    "(상위${String.format("%.2f", it.expRankPercentile)}%)" // 현재 백분위

                binding.tvCurrentExpMypage.text =
                    it.exp.toString() + "포인트" // 총 경험치


                // 다음 등급까지 남은 포인트
                binding.tvRemainExpMypage.text =
                    "다음 등급까지 남은 포인트는 ${it.nextLevelExp - it.exp}P입니다"
                binding.tvMaxExpMypage.text = it.nextLevelExp.toString() + "P"

                // 프로그레스바 계산
                binding.progressbarExpMypage.min =
                    baseExp[getCurrentGrade(it.exp)]
                binding.progressbarExpMypage.max = baseExp[getNextGrade(it.exp)]
                binding.progressbarExpMypage.progress = it.exp

                // 엠블렘 이미지 업데이트
                binding.ivEmblem2Mypage.setImageResource(
                    baseExpLevelImage[getCurrentGrade(
                        it.exp
                    )]
                )
                binding.ivNextEmblemMypage.setImageResource(
                    baseExpLevelImage[getNextGrade(
                        it.exp
                    )]
                )

                val defaultImage = R.color.light_gray
                Glide.with(this)
                    .load(it.member.profileUrl) // 불러올 이미지 url
                    .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                    .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                    .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                    .into(binding.civProfileMypage) // 이미지를 넣을 뷰

                isNotificationEnabled = it.member.isNotificationEnabled

                // UI 업데이트를 위한 메서드 호출
                updateNotificationButtonUI(isNotificationEnabled)
            }
        }
    }

    fun updateMyPageUiWithData() {
        val accessToken = TokenUtil().getAccessToken(this@MyPageActivity)
        memberViewModel.getMemberProfile("Bearer ${accessToken!!}")
        updateNotificationButtonUI(isNotificationEnabled)
    }

    private fun updateNotificationButtonUI(isEnabled: Boolean) {
        if (isEnabled) {
            binding.tvStateNotificationMypage.text = "알림 켜짐"
            binding.btnNotificationMypage.setImageResource(R.drawable.ic_notification_on)
            binding.btnNotificationMypage.setOnClickListener {
                updateNotificationReception(false)
            }
        } else {
            binding.tvStateNotificationMypage.text = "알림 꺼짐"
            binding.btnNotificationMypage.setImageResource(R.drawable.ic_notification_off)
            binding.btnNotificationMypage.setOnClickListener {
                updateNotificationReception(true)
            }
        }
    }

    fun updateNotificationReception(isEnabled: Boolean) {
        val accessToken = TokenUtil().getAccessToken(this)  // AccessToken을 가져옵니다.

        val isNotificationEnabledBody = RequestBody.create(
            "text/plain".toMediaTypeOrNull(),
            isEnabled.toString()
        )

        RetrofitClient.memberApiService.updateNotificationReception(
            "Bearer $accessToken",
            isNotificationEnabledBody
        ).enqueue(object : Callback<MemberResponse> {
            override fun onResponse(call: Call<MemberResponse>, response: Response<MemberResponse>) {
                if (response.isSuccessful) {
                    // 알림 수신 설정이 성공적으로 업데이트됨
                    val updatedMember = response.body()
                    // 성공적으로 업데이트된 회원 정보로 UI 업데이트 등의 작업을 수행할 수 있습니다.
                    Log.d("API", "updateNotificationButton: ${updatedMember}")

                    Toast.makeText(this@MyPageActivity, "알림 ${isEnabled}", Toast.LENGTH_SHORT).show()
                } else {
                    // API 호출 실패 처리
                    Log.e("API", "onResponse: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MemberResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("API", "onFailure: ${t.message}")
            }
        })
    }

    fun getCurrentGrade(exp : Int) : Int {
        val currentGrade = when {
            exp >= 5000 -> 3
            exp in 2000..4999 -> 2
            exp in 500..1999 -> 1
            else -> 0
        }

        return currentGrade
    }

    fun getNextGrade(exp : Int) : Int {
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