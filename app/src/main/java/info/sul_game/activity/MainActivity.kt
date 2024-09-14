package info.sul_game.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import info.sul_game.R
import info.sul_game.databinding.ActivityMainBinding
import info.sul_game.fragment.MyPostFragment
import info.sul_game.recyclerview.DrinkingGameAdapter
import info.sul_game.recyclerview.DrinkingGameItem
import info.sul_game.recyclerview.GameAdapter
import info.sul_game.recyclerview.GameItem
import info.sul_game.recyclerview.LiveChartAdapter
import info.sul_game.recyclerview.LiveChartItem
import info.sul_game.ui.mypage.BookmarkedPostFragment
import info.sul_game.ui.mypage.EditAccountFragment
import info.sul_game.ui.mypage.LikedPostFragment
import info.sul_game.utils.TokenUtil
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    // BottomSheet가 완전히 펼쳐진 상태인지 확인할 수 있는 변수
    private var isBottomSheetExpanded: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        persistentBottomSheetEvent()
        recyclerMain()
    }

    private fun recyclerMain() {
        recentRecyclerMain()
        popularGameRecyclerMain()
        liveChartRecyclerMain()
        shareIntroRecyclerMain()
        hotGameRecyclerMain()
    }

    // 함수를 통해 데이터를 불러온다.
    fun JSON_Parse(obj: JSONObject, data: String): String {
        // 원하는 정보를 불러와 리턴받고 없는 정보는 캐치하여 "없습니다."로 리턴받는다.
        return try {
            obj.getString(data)
        } catch (e: Exception) {
            "없습니다."
        }
    }

    /** 리사이클러뷰 변수 선언 */
    private val gameItemIntroData = arrayListOf<GameItem>()

    inner class NetworkThread : Thread() {
        override fun run() {
            val url = URL("https://api.sul-game.info/api/home")
            val conn = url.openConnection()
            val input = conn.getInputStream()
            val isr = InputStreamReader(input)
            // br: 라인 단위로 데이터를 읽어오기 위해서 만듦
            val br = BufferedReader(isr)

            // Json 문서는 일단 문자열로 데이터를 모두 읽어온 후, Json에 관련된 객체를 만들어서 데이터를 가져옴
            var str: String? = null
            val buf = StringBuffer()

            do {
                str = br.readLine()

                if (str != null) {
                    buf.append(str)
                }
            } while (str != null)

            // 전체가 객체로 묶여있기 때문에 객체형태로 가져옴
            val root = JSONObject(buf.toString())
            val response = root.getJSONArray("latestIntros")
            for (i in 0 until response.length()) {
                val item = response.getJSONObject(i)
                val jObject = response.getJSONObject(i)
                val title = JSON_Parse(jObject, "title")
                val contents = JSON_Parse(jObject, "description")
                val nickname = item.getString("title")
                val cntHeart = JSON_Parse(jObject, "likes")
                gameItemIntroData.add(
                    GameItem(
                        title,
                        contents,
                        nickname.toString(),
                        cntHeart.toInt()
                    )
                )
                gameItemIntroData.add(
                    GameItem(
                        title,
                        contents,
                        nickname.toString(),
                        cntHeart.toInt()
                    )
                )
                gameItemIntroData.add(
                    GameItem(
                        title,
                        contents,
                        nickname.toString(),
                        cntHeart.toInt()
                    )
                )
            }
        }
    }

    // 최신 게시물의 리사이클러뷰
    private fun recentRecyclerMain() {
        val gameItemCreationData = arrayListOf<GameItem>(
            GameItem("어목조동", "자연과 함께하는 술게임", "구해조", 30),
            GameItem("딸기당근수박참외 찍고", "지목이 더해진 과일게임", "구해조", 30),
            GameItem("딸기당근수박참외 리버스", "거꾸로 말하기 도전!", "구해조", 30)
        )

//        val thread = NetworkThread()
//        thread.start()
//        thread.join()

        binding.rvRecentMain.adapter = GameAdapter(gameItemCreationData)
        binding.rvRecentMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        binding.tvRecentCreationMain.setOnClickListener {
            binding.tvRecentCreationMain.setTextColor(getColor(R.color.main_color))
            binding.tvRecentIntroMain.setTextColor(
                android.graphics.Color.parseColor(
                    "#8C8C8C"
                )
            )

            binding.rvRecentMain.adapter = GameAdapter(gameItemCreationData)
            binding.rvRecentMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        binding.tvRecentIntroMain.setOnClickListener {
            binding.tvRecentCreationMain.setTextColor(
                android.graphics.Color.parseColor(
                    "#8C8C8C"
                )
            )
            binding.tvRecentIntroMain.setTextColor(getColor(R.color.main_color))

            binding.rvRecentMain.adapter = GameAdapter(gameItemIntroData)
            binding.rvRecentMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun popularGameRecyclerMain() {
        val popularGameData = arrayListOf<DrinkingGameItem>(
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            )
        )

        binding.rvPopularMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                3,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = DrinkingGameAdapter(popularGameData)
        }
    }

    private fun liveChartRecyclerMain() {
        val liveChartItemCreationData = arrayListOf<LiveChartItem>(
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 창작",
                "하늘에서 내려와버린 토끼",
                30
            )
        )

        val liveChartItemIntroData = arrayListOf<LiveChartItem>(
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 인트로",
                "하늘에서 내려와버린 토끼",
                30
            )
        )

        val liveChartItemPopularData = arrayListOf<LiveChartItem>(
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            ),
            LiveChartItem(
                R.drawable.ic_launcher_background,
                "바니바니 국룰",
                "하늘에서 내려와버린 토끼",
                30
            )
        )

        binding.rvLiveMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                3,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = LiveChartAdapter(liveChartItemCreationData)
        }

        // 각 Chip에 클릭 리스너 추가 (선택 상태 유지 보장)
        binding.cpCreationMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpCreationMain)
            binding.rvLiveMain.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(
                    this@MainActivity,
                    3,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = LiveChartAdapter(liveChartItemCreationData)
            }
        }
        binding.cpIntroMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpIntroMain)
            binding.rvLiveMain.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(
                    this@MainActivity,
                    3,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = LiveChartAdapter(liveChartItemIntroData)
            }
        }
        binding.cpPopularMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpPopularMain)
            binding.rvLiveMain.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(
                    this@MainActivity,
                    3,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = LiveChartAdapter(liveChartItemPopularData)
            }
        }
    }

    private fun shareIntroRecyclerMain() {
        val shareIntroRecentData = arrayListOf<GameItem>(
            GameItem("어목조동 최신", "자연과 함께하는 술게임", "구해조", 30),
            GameItem("딸기당근수박참외 찍고 최신", "지목이 더해진 과일게임", "구해조", 30),
            GameItem("딸기당근수박참외 리버스 최신", "거꾸로 말하기 도전!", "구해조", 30)
        )

        val shareIntroHeartData = arrayListOf<GameItem>(
            GameItem("어목조동 좋아요", "자연과 함께하는 술게임", "구해조", 30),
            GameItem("딸기당근수박참외 찍고 좋아요", "지목이 더해진 과일게임", "구해조", 30),
            GameItem("딸기당근수박참외 리버스 좋아요", "거꾸로 말하기 도전!", "구해조", 30)
        )

        val shareIntroViewData = arrayListOf<GameItem>(
            GameItem("어목조동 조회수", "자연과 함께하는 술게임", "구해조", 30),
            GameItem("딸기당근수박참외 찍고 조회수", "지목이 더해진 과일게임", "구해조", 30),
            GameItem("딸기당근수박참외 리버스 조회수", "거꾸로 말하기 도전!", "구해조", 30)
        )

        binding.rvShareIntroMain.adapter = GameAdapter(shareIntroRecentData)
        binding.rvShareIntroMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 각 Chip에 클릭 리스너 추가 (선택 상태 유지 보장)
        binding.cpRecentMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpRecentMain)
            binding.rvShareIntroMain.adapter = GameAdapter(shareIntroRecentData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.cpHeartMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpHeartMain)
            binding.rvShareIntroMain.adapter = GameAdapter(shareIntroHeartData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.cpViewMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpViewMain)
            binding.rvShareIntroMain.adapter = GameAdapter(shareIntroViewData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun hotGameRecyclerMain() {
        val hotGameData = arrayListOf<DrinkingGameItem>(
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            ),
            DrinkingGameItem(
                R.drawable.ic_launcher_background,
                "바니바니",
                "하늘에서 내려와버린 토끼",
                30
            )
        )

        binding.rvHotMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                3,
                GridLayoutManager.HORIZONTAL,
                false
            )
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

    // 화면 초기화 시 호출되는 bottomSheet에서의 이벤트 처리를 위한 함수
    private fun persistentBottomSheetEvent() {

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
                    BottomSheetBehavior.STATE_DRAGGING -> {
                        binding.backgroundDimMain.visibility = View.VISIBLE
                        Log.d("술겜위키", "refreshToken : ${TokenUtil().getRefreshToken(this@MainActivity)}")
                        if(TokenUtil().getRefreshToken(this@MainActivity).isNullOrBlank()){
                            startActivity(Intent(this@MainActivity, SignInActivity::class.java))
                            finish()
                        }
                    }

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

        binding.btnMypostMypage.setOnClickListener { showBottomSheetFragment(
            MyPostFragment(), "내 글 보기") }
        binding.btnBookmarkMypage.setOnClickListener { showBottomSheetFragment(
            BookmarkedPostFragment(), "즐겨찾기") }
        binding.btnLikedpostMypage.setOnClickListener { showBottomSheetFragment(
            LikedPostFragment(), "좋아요 게시글") }
        binding.btnProfileSettingMypage.setOnClickListener { showBottomSheetFragment(
            EditAccountFragment(), "계정 설정") }
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