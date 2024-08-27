package com.example.sul_game_frontend_practice1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
import com.example.sul_game_frontend_practice1.mypage.FavorPost
import com.example.sul_game_frontend_practice1.mypage.FavorPostAdapter
import com.example.sul_game_frontend_practice1.mypage.MyPost
import com.example.sul_game_frontend_practice1.mypage.MyPostAdapter
import com.example.sul_game_frontend_practice1.recyclerview.DrinkingGame.DrinkingGame
import com.example.sul_game_frontend_practice1.recyclerview.DrinkingGame.DrinkingGameAdapter
import com.example.sul_game_frontend_practice1.recyclerview.Game.Game
import com.example.sul_game_frontend_practice1.recyclerview.Game.GameAdapter
import com.example.sul_game_frontend_practice1.recyclerview.LiveChart.LiveChart
import com.example.sul_game_frontend_practice1.recyclerview.LiveChart.LiveChartAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initEvent()
        persistentBottomSheetEvent()
        recyclerMain()
    }

    private fun recyclerMain(){
        recentRecyclerMain()
        popularGameRecyclerMain()
        liveChartRecyclerMain()
        shareIntroRecyclerMain()
        hotGameRecyclerMain()
    }

    // 최신 게시물의 리사이클러뷰
    private fun recentRecyclerMain(){
        val gameCreationData = arrayListOf<Game>(
            Game("어목조동", "자연과 함께하는 술게임", "구해조", 30),
            Game("딸기당근수박참외 찍고", "지목이 더해진 과일게임", "구해조", 30),
            Game("딸기당근수박참외 리버스", "거꾸로 말하기 도전!", "구해조", 30)
        )

        val gameIntroData = arrayListOf<Game>(
            Game("어목조동 intro", "자연과 함께하는 술게임", "구해조", 30),
            Game("딸기당근수박참외 찍고 intro", "지목이 더해진 과일게임", "구해조", 30),
            Game("딸기당근수박참외 리버스 intro", "거꾸로 말하기 도전!", "구해조", 30)
        )

        binding.rvRecentMain.adapter = GameAdapter(gameCreationData)
        binding.rvRecentMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.tvRecentCreationMain.setOnClickListener{
            binding.tvRecentCreationMain.setTextColor(getColor(R.color.main_color))
            binding.tvRecentIntroMain.setTextColor(android.graphics.Color.parseColor("#8C8C8C"))

            binding.rvRecentMain.adapter = GameAdapter(gameCreationData)
            binding.rvRecentMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        binding.tvRecentIntroMain.setOnClickListener{
            binding.tvRecentCreationMain.setTextColor(android.graphics.Color.parseColor("#8C8C8C"))
            binding.tvRecentIntroMain.setTextColor(getColor(R.color.main_color))

            binding.rvRecentMain.adapter = GameAdapter(gameIntroData)
            binding.rvRecentMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun popularGameRecyclerMain(){
        val popularGameData = arrayListOf<DrinkingGame>(
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30)
        )

        binding.rvPopularMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
            adapter = DrinkingGameAdapter(popularGameData)
        }
    }

    private fun liveChartRecyclerMain(){
        val liveChartCreationData = arrayListOf<LiveChart>(
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 창작", "하늘에서 내려와버린 토끼", 30)
        )

        val liveChartIntroData = arrayListOf<LiveChart>(
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 인트로", "하늘에서 내려와버린 토끼", 30)
        )

        val liveChartPopularData = arrayListOf<LiveChart>(
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30),
            LiveChart(R.drawable.ic_launcher_background, "바니바니 국룰", "하늘에서 내려와버린 토끼", 30)
        )

        binding.rvLiveMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
            adapter = LiveChartAdapter(liveChartCreationData)
        }

        // 각 Chip에 클릭 리스너 추가 (선택 상태 유지 보장)
        binding.cpCreationMain.setOnClickListener {
                v -> ensureOneSelected(binding.cpCreationMain)
                binding.rvLiveMain.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
                    adapter = LiveChartAdapter(liveChartCreationData)
                }
        }
        binding.cpIntroMain.setOnClickListener {
                v -> ensureOneSelected(binding.cpIntroMain)
                binding.rvLiveMain.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
                    adapter = LiveChartAdapter(liveChartIntroData)
                }
        }
        binding.cpPopularMain.setOnClickListener {
                v -> ensureOneSelected(binding.cpPopularMain)
                binding.rvLiveMain.apply {
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
                    adapter = LiveChartAdapter(liveChartPopularData)
                }
        }
    }

    private fun shareIntroRecyclerMain(){
        val shareIntroRecentData = arrayListOf<Game>(
            Game("어목조동 최신", "자연과 함께하는 술게임", "구해조", 30),
            Game("딸기당근수박참외 찍고 최신", "지목이 더해진 과일게임", "구해조", 30),
            Game("딸기당근수박참외 리버스 최신", "거꾸로 말하기 도전!", "구해조", 30)
        )

        val shareIntroHeartData = arrayListOf<Game>(
            Game("어목조동 좋아요", "자연과 함께하는 술게임", "구해조", 30),
            Game("딸기당근수박참외 찍고 좋아요", "지목이 더해진 과일게임", "구해조", 30),
            Game("딸기당근수박참외 리버스 좋아요", "거꾸로 말하기 도전!", "구해조", 30)
        )

        val shareIntroViewData = arrayListOf<Game>(
            Game("어목조동 조회수", "자연과 함께하는 술게임", "구해조", 30),
            Game("딸기당근수박참외 찍고 조회수", "지목이 더해진 과일게임", "구해조", 30),
            Game("딸기당근수박참외 리버스 조회수", "거꾸로 말하기 도전!", "구해조", 30)
        )

        binding.rvShareIntroMain.adapter = GameAdapter(shareIntroRecentData)
        binding.rvShareIntroMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 각 Chip에 클릭 리스너 추가 (선택 상태 유지 보장)
        binding.cpRecentMain.setOnClickListener {
            v -> ensureOneSelected(binding.cpRecentMain)
            binding.rvShareIntroMain.adapter = GameAdapter(shareIntroRecentData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.cpHeartMain.setOnClickListener {
            v -> ensureOneSelected(binding.cpHeartMain)
            binding.rvShareIntroMain.adapter = GameAdapter(shareIntroHeartData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.cpViewMain.setOnClickListener {
            v -> ensureOneSelected(binding.cpViewMain)
            binding.rvShareIntroMain.adapter = GameAdapter(shareIntroViewData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun hotGameRecyclerMain(){
        val hotGameData = arrayListOf<DrinkingGame>(
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30),
            DrinkingGame(R.drawable.ic_launcher_background, "바니바니", "하늘에서 내려와버린 토끼", 30)
        )

        binding.rvHotMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MainActivity, 3, GridLayoutManager.HORIZONTAL, false)
            adapter = DrinkingGameAdapter(hotGameData)
        }
    }

    private fun ensureOneSelected(selectedChip: Chip) {
        if (!selectedChip.isChecked) {
            // 선택된 Chip을 선택 해제한 경우
            binding.cgShareIntroChipsMain.clearCheck()
            selectedChip.isChecked = true
        }
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
        binding.btnMyinfoMypage.setOnClickListener { setDisplayedChildBottomSheet(MYINFO) }

        // 프로필 편집 처리
        binding.btnSumbitEditprofile.setOnClickListener {
            setDisplayedChildBottomSheet(MYPAGE)
        }

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