package info.sul_game.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL

import info.sul_game.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import info.sul_game.ui.search.SearchActivity
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import info.sul_game.ui.mypage.BookmarkedPost
import info.sul_game.ui.mypage.BookmarkedPostAdapter
import info.sul_game.ui.mypage.LikedPost
import info.sul_game.ui.mypage.LikedPostAdapter
import info.sul_game.ui.mypage.MyPost
import info.sul_game.ui.mypage.MyPostAdapter
import info.sul_game.R
import info.sul_game.data.source.remote.MyApplication
import info.sul_game.data.source.remote.NetworkManager
import info.sul_game.ui.mypage.BookmarkedPostFragment
import info.sul_game.ui.mypage.EditAccountFragment
import info.sul_game.ui.mypage.LikedPostFragment
import info.sul_game.ui.mypage.MyPostFragment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    // BottomSheet가 완전히 펼쳐진 상태인지 확인할 수 있는 변수
    private var isBottomSheetExpanded : Boolean = false


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

        binding.searchviewMain.setOnClickListener {
            if (!isBottomSheetExpanded) {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }


        // 뒤로가기 버튼 처리를 위한 콜백 추가
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    // Fragment가 있으면 뒤로가기 시 Fragment 제거
                    supportFragmentManager.popBackStack()
                    // 버튼 목록 다시 표시하고 타이틀 변경
                    binding.containerMainBottomsheet.visibility = View.VISIBLE
                    binding.fragmentContainerMypage.visibility = View.GONE
                    binding.tvTitleMypage.text = "마이페이지"
                } else {
                    // Fragment가 없으면 기본 뒤로가기 처리
                    finish()
                }
            }
        })
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

        binding.btnMypostMypage.setOnClickListener { showBottomSheetFragment(MyPostFragment(), "내 글 보기") }
        binding.btnBookmarkMypage.setOnClickListener { showBottomSheetFragment(BookmarkedPostFragment(), "즐겨찾기") }
        binding.btnLikedpostMypage.setOnClickListener { showBottomSheetFragment(LikedPostFragment(), "좋아요 게시글") }
        binding.btnProfileSettingMypage.setOnClickListener { showBottomSheetFragment(EditAccountFragment(), "계정 설정") }
    }

    // 멤버 데이터가 필요한 UI 업데이트 함수
    private fun updateUIWithMemberData() {
        val app = application as MyApplication
        val member = app.member
        val memberContentInteraction = app.memberContentInteraction

        member?.let {
            // 마이 페이지 처리
            binding.tvUsernameMypage.text = member.nickname
            binding.tvUniversityMypage.text = member.college

            val defaultImage = R.color.light_gray
            Glide.with(this)
                .load(member.profileUrl) // 불러올 이미지 url
                .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .circleCrop() // 동그랗게 자르기
                .into(binding.civProfileMypage) // 이미지를 넣을 뷰

            // 내 정보
            binding.tvExpMypage.text = "${member.exp}포인트"
            binding.progressbarExpMypage.progress = member.exp
        }
    }

    // Fragment 전환 함수
    private fun showBottomSheetFragment(fragment: Fragment, title: String){
        binding.containerMainBottomsheet.visibility = View.GONE // 마이페이지 화면 숨김
        binding.fragmentContainerMypage.visibility = View.VISIBLE // Fragment 컨테이너 표시
        binding.tvTitleMypage.text = title // 타이틀 변경

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_mypage, fragment)
            .addToBackStack(null)
            .commit()
    }

}