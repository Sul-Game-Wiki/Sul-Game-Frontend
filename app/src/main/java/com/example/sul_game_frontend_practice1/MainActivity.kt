package com.example.sul_game_frontend_practice1

import android.content.Intent
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
import com.example.sul_game_frontend_practice1.mypage.FavorPost
import com.example.sul_game_frontend_practice1.mypage.FavorPostAdapter
import com.example.sul_game_frontend_practice1.mypage.MyPost
import com.example.sul_game_frontend_practice1.mypage.MyPostAdapter
import com.example.sul_game_frontend_practice1.retrofit.ApiService
import com.example.sul_game_frontend_practice1.retrofit.Member
import com.example.sul_game_frontend_practice1.retrofit.MemberContentInteraction
import com.example.sul_game_frontend_practice1.retrofit.NicknameResponse
import com.example.sul_game_frontend_practice1.retrofit.ProfileResponse
import com.example.sul_game_frontend_practice1.retrofit.RetrofitClient
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var apiService: ApiService

    // BottomSheet가 완전히 펼쳐진 상태인지 확인할 수 있는 변수
    private var isBottomSheetExpanded : Boolean = false

    /* 현재 열려있는 BottomSheet의 Child를 확인할 수 있는 변수
    0 : 마이페이지, 1 : 내게시글, 2 : 즐겨찾기게시글, 3 : 프로필 수정, 4 : 내 정보 보기*/
    private var displayedChildBottomSheet : Int = 0
    private companion object {
        const val MYPAGE = 0
        const val MYPOST = 1
        const val FAVORPOST = 2
        const val EDITPROFILE = 3
        const val MYINFO = 4
    }

    private var memberId = 5L
    private lateinit var member: Member
    private lateinit var memberContentInteraction: MemberContentInteraction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getProfile(memberId)

        initEvent()
        persistentBottomSheetEvent()

    }

    // 화면 초기화 시 호출되는 함수
    private fun initEvent() {
        isBottomSheetExpanded = false
        setDisplayedChildBottomSheet(MYPAGE)

        binding.searchviewMain.setOnClickListener {
            if (!isBottomSheetExpanded) {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }

    }

    // 화면 초기화 시 호출되는 bottomSheet에서의 이벤트 처리를 위한 함수
    private fun persistentBottomSheetEvent() {
        behavior = BottomSheetBehavior.from(binding.bottomSheetMain)
        behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    // 완전히 펼쳐졌을 때
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        binding.searchviewMain.isClickable = false
                        isBottomSheetExpanded = true
                    }

                    // 드래깅 되고있을 때
                    BottomSheetBehavior.STATE_DRAGGING ->
                        binding.backgroundDimMain.visibility = View.VISIBLE

                    // 접혀있을 때
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.backgroundDimMain.visibility = View.GONE
                        binding.searchviewMain.isClickable = true
                        isBottomSheetExpanded = false
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.backgroundDimMain.alpha = slideOffset
            }
        })

        // Set up other UI elements that do not depend on member data here
    }

    private fun updateUIWithMemberData() {
        // 마이 페이지 처리
        binding.tvUsernameMypage.text = member.nickname
        binding.tvUniversityMypage.text = member.college
        binding.btnMypostMypage.setOnClickListener { setDisplayedChildBottomSheet(MYPOST) }
        binding.btnFavorpostMypage.setOnClickListener { setDisplayedChildBottomSheet(FAVORPOST) }
        binding.btnEditprofileMypage.setOnClickListener { setDisplayedChildBottomSheet(EDITPROFILE) }
        binding.btnMyinfoMypage.setOnClickListener { setDisplayedChildBottomSheet(MYINFO) }

        // 프로필 편집 처리
        binding.btnSumbitEditprofile.setOnClickListener {
            setDisplayedChildBottomSheet(MYPAGE)
        }
        binding.et1Editprofile.setText(member.nickname)
        binding.et2Editprofile.setText(member.email)
        binding.et3Editprofile.setText(member.birthDate)
        binding.et4Editprofile.setText(member.college)


        // 내 게시글 처리
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val myPostList = mutableListOf<MyPost>()
        val favorPostList = mutableListOf<FavorPost>()

        myPostList.add(MyPost("어목조동", "자연과 함께하는 술게임"))
        myPostList.add(MyPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임"))

        favorPostList.add(FavorPost("어목조동", "자연과 함께하는 술게임", true, "구해조"))
        favorPostList.add(FavorPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임", true, "구해조"))

        binding.recyclerviewMypost.adapter = MyPostAdapter(myPostList)
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewMypost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        binding.recyclerviewFavorpost.adapter = FavorPostAdapter(favorPostList)
        binding.recyclerviewFavorpost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewFavorpost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

    }

    // BottomSheet의 child 설정
    private fun setDisplayedChildBottomSheet(child: Int){
        val bottomSheetTitle = arrayListOf<String>("마이 페이지", "내 게시글", "즐겨찾기 게시글", "프로필 편집", "내 정보보기")

        binding.viewflipperBottomsheet.displayedChild = child
        binding.tvTitleMypage.text = bottomSheetTitle[child]
        displayedChildBottomSheet = child
    }

    private fun getProfile(memberId : Long) {
        // API 호출
        RetrofitClient.apiService
            .getMemberProfile(memberId)
            .enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(call: Call<ProfileResponse>, response: Response<ProfileResponse>) {
                if (response.isSuccessful) {
                    // API 응답이 성공적일 때 데이터 처리
                    val profileResponse = response.body()
                    profileResponse?.let {
                        member = it.member
                        memberContentInteraction = it.memberContentInteraction
                        Log.d("API_RESPONSE", "Member: $member")
                        Log.d("API_RESPONSE", "MemberContentInteraction: $memberContentInteraction")

                        updateUIWithMemberData()
                    } ?: run {
                        Log.e("API_ERROR", "Response body is null")
                    }
                } else {
                    Log.e("API_ERROR", "Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                // 네트워크 오류 또는 기타 오류 처리
                Log.e("API_ERROR", "Failure: ${t.message}")
            }
        })
    }

    private fun updateNickname(memberId: Long, newNickname: String) {
        // API 호출
        RetrofitClient.apiService
            .updateNickname(memberId, newNickname)
            .enqueue(object : Callback<NicknameResponse> {
                override fun onResponse(
                    call: Call<NicknameResponse>,
                    response: Response<NicknameResponse>
                ) {
                    if (response.isSuccessful) {
                        // API 응답이 성공적일 때 데이터 처리
                        val nicknameResponse = response.body()
                        nicknameResponse?.let {
                            member = it.member
                            Log.d("API_RESPONSE", "Member: $member")
                            Toast.makeText(this@MainActivity, "닉네임 변경 성공!", Toast.LENGTH_SHORT).show()

                            updateUIWithMemberData()
                        } ?: run {
                            Log.e("API_ERROR", "Response body is null")
                        }
                    } else {
                        Log.e("API_ERROR", "Error: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(
                    call: Call<NicknameResponse>,
                    t: Throwable
                ) {
                    // 네트워크 오류 또는 기타 오류 처리
                    Log.e("API_ERROR", "Failure: ${t.message}")
                }
            })
    }

    // 뒤로가기 버튼 처리
    override fun onBackPressed() {
        if(isBottomSheetExpanded && displayedChildBottomSheet > MYPAGE){
            setDisplayedChildBottomSheet(0)
        }
        else{
            super.onBackPressed()
        }
    }

}