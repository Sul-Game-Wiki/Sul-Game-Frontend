package com.example.sul_game_frontend_practice1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    // BottomSheet가 완전히 펼쳐진 상태인지 확인할 수 있는 변수
    private var isBottomSheetExpanded : Boolean = false

    /* 현재 열려있는 BottomSheet의 Child를 확인할 수 있는 변수
    0 : 마이페이지, 1 : 내게시글, 2 : 즐겨찾기게시글, 3 : 프로필 수정 */
    private var displayedChildBottomSheet : Int = 0
    companion object {
        const val MYPAGE = 0
        const val MYPOST = 1
        const val FAVORPOST = 2
        const val EDITPROFILE = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            } })


        // 마이 페이지 처리
        binding.tvUsernameMypage.text = "구해조"
        binding.tvUniversityMypage.text = "세종대학교"
        binding.btnMypostMypage.setOnClickListener { setDisplayedChildBottomSheet(MYPOST) }
        binding.btnFavorpostMypage.setOnClickListener { setDisplayedChildBottomSheet(FAVORPOST) }
        binding.btnEditprofileMypage.setOnClickListener { setDisplayedChildBottomSheet(EDITPROFILE) }

        // 프로필 편집 처리
        binding.btnSumbitEditprofile.setOnClickListener {
            setDisplayedChildBottomSheet(MYPAGE)
        }


    }

    // BottomSheet의 child 설정
    private fun setDisplayedChildBottomSheet(child: Int){
        val bottomSheetTitle = arrayListOf<String>("마이 페이지", "내 게시글", "즐겨찾기 게시글", "프로필 편집")

        binding.viewflipperBottomsheet.displayedChild = child
        binding.tvTitleMypage.text = bottomSheetTitle[child]
        displayedChildBottomSheet = child
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