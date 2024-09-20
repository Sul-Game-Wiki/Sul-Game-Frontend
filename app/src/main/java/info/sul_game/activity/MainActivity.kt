package info.sul_game.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import info.sul_game.R
import info.sul_game.config.RetrofitClient
import info.sul_game.databinding.ActivityMainBinding
import info.sul_game.model.HomeResponse
import info.sul_game.model.MemberResponse
import info.sul_game.model.RankingHistoryPage
import info.sul_game.recyclerview.DrinkingGameMainAdapter
import info.sul_game.recyclerview.DrinkingGameMainItem
import info.sul_game.recyclerview.IntroMainAdapter
import info.sul_game.recyclerview.IntroMainItem
import info.sul_game.recyclerview.LatestFeedMainAdapter
import info.sul_game.recyclerview.LatestFeedMainDecoration
import info.sul_game.recyclerview.LatestFeedMainItem
import info.sul_game.recyclerview.LiveChartMainAdapter
import info.sul_game.recyclerview.LiveChartMainItem
import info.sul_game.recyclerview.UserRankingMainAdapter
import info.sul_game.recyclerview.UserRankingMainItem
import info.sul_game.utils.TokenUtil
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>
    private val handler = Handler(Looper.getMainLooper())
    private var userInteractionTimeout: Long = 2000 // 2초 후에 FAB 숨김
    private lateinit var progressDialog: ProgressDialog

    // 리사이클러뷰 아이템 리스트
    private var latestFeedMainItemCreationData: ArrayList<LatestFeedMainItem> = ArrayList()
    private var latestFeedMainItemIntroData: ArrayList<LatestFeedMainItem> = ArrayList()
    private var officialGameData: ArrayList<DrinkingGameMainItem> = ArrayList()
    private var liveChartMainItemCreationData: ArrayList<LiveChartMainItem> = ArrayList() /////
    private var liveChartMainItemIntroData: ArrayList<LiveChartMainItem> = ArrayList()
    private var liveChartMainItemOfficialData: ArrayList<LiveChartMainItem> = ArrayList()
    private var shareIntroRecentData: ArrayList<IntroMainItem> = ArrayList()
    private var shareIntroLikesData: ArrayList<IntroMainItem> = ArrayList()
    private var shareIntroViewData: ArrayList<IntroMainItem> = ArrayList()
    private var hotWeeklyGameData: ArrayList<DrinkingGameMainItem> = ArrayList()
    private var hotDailyGameData: ArrayList<DrinkingGameMainItem> = ArrayList()
    private var userRankingData: ArrayList<UserRankingMainItem> = ArrayList()

    private val hideFabRunnable = Runnable {
        binding.expandableFab.hide()  // 일정 시간 후 FloatingActionButton 숨기기
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this).apply {
            setMessage("로딩 중입니다...")
            setCancelable(false)  // 로딩 중에는 취소 불가
        }
        showLoadingDialog()

        getUserRank()
        getAllData()

        val prefs = getSharedPreferences("prefs", MODE_PRIVATE)
        val firstTime = prefs.getBoolean("firstTime", true)

        if (firstTime) {
            startActivity(Intent(this, WelcomeGuideActivity::class.java))
            finish()
        }

        findViewById<View>(android.R.id.content).setOnTouchListener { _, _ ->
            resetFabTimer() // 사용자 입력 발생 시 타이머 리셋
            false
        }

        persistentBottomSheetEvent()
        startFabTimer()

        if(TokenUtil().getRefreshToken(this@MainActivity).isNullOrBlank()) {
            binding.tvMypageLoginMain.text = "마이페이지 서비스는\n로그인이 필요해요!"
            binding.tvMypageLoginMain.textSize = 18f
        } else {
            binding.tvMypageLoginMain.text = "마이 페이지"
            binding.tvMypageLoginMain.textSize = 32f
        }

        // Firebase 초기화
        FirebaseApp.initializeApp(this)

        // 그 후에 FCM 토큰 관련 작업 수행
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("술겜위키", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            val token = task.result
            Log.d("술겜위키", "토큰 이름 : $token")
        }
    }

    private fun recyclerMain() {
        recentRecyclerMain()
        popularGameRecyclerMain()
        liveChartRecyclerMain()
        shareIntroRecyclerMain()
        hotGameRecyclerMain()
        userRankingRecyclerMain()
    }

    /***************************************************************************************************
     * 리사이클러뷰 소스코드 시작
     ****************************************************************************************************/

    // 최신 게시물의 리사이클러뷰
    private fun recentRecyclerMain() {
        binding.rvRecentMain.adapter = LatestFeedMainAdapter(this, latestFeedMainItemCreationData)
        binding.rvRecentMain.addItemDecoration(LatestFeedMainDecoration())
        binding.rvRecentMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.tvRecentCreationMain.setOnClickListener {
            binding.tvRecentCreationMain.setTextColor(getColor(R.color.main_color))
            binding.tvRecentIntroMain.setTextColor(
                android.graphics.Color.parseColor(
                    "#8C8C8C"
                )
            )

            binding.rvRecentMain.adapter = LatestFeedMainAdapter(this, latestFeedMainItemCreationData)
            binding.rvRecentMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.tvRecentIntroMain.setOnClickListener {
            binding.tvRecentCreationMain.setTextColor(
                android.graphics.Color.parseColor(
                    "#8C8C8C"
                )
            )
            binding.tvRecentIntroMain.setTextColor(getColor(R.color.main_color))

            binding.rvRecentMain.adapter = LatestFeedMainAdapter(this, latestFeedMainItemIntroData)
            binding.rvRecentMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun popularGameRecyclerMain() {
        binding.rvPopularMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                3,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = DrinkingGameMainAdapter(this@MainActivity, officialGameData)
        }
    }

    private fun liveChartRecyclerMain() {
        binding.rvLiveMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                3,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = LiveChartMainAdapter(this@MainActivity, liveChartMainItemCreationData)
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
                adapter = LiveChartMainAdapter(this@MainActivity, liveChartMainItemCreationData)
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
                adapter = LiveChartMainAdapter(this@MainActivity, liveChartMainItemIntroData)
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
                adapter = LiveChartMainAdapter(this@MainActivity, liveChartMainItemOfficialData)
            }
        }
    }

    private fun shareIntroRecyclerMain() {
        binding.rvShareIntroMain.adapter = IntroMainAdapter(shareIntroRecentData)
        binding.rvShareIntroMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        // 각 Chip에 클릭 리스너 추가 (선택 상태 유지 보장)
        binding.cpRecentMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpRecentMain)
            binding.rvShareIntroMain.adapter = IntroMainAdapter(shareIntroRecentData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.cpHeartMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpHeartMain)
            binding.rvShareIntroMain.adapter = IntroMainAdapter(shareIntroLikesData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
        binding.cpViewMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpViewMain)
            binding.rvShareIntroMain.adapter = IntroMainAdapter(shareIntroViewData)
            binding.rvShareIntroMain.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun hotGameRecyclerMain() {
        binding.rvHotMain.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(
                this@MainActivity,
                3,
                GridLayoutManager.HORIZONTAL,
                false
            )
            adapter = DrinkingGameMainAdapter(this@MainActivity, hotWeeklyGameData)
        }

        // 각 Chip에 클릭 리스너 추가 (선택 상태 유지 보장)
        binding.cpWeeklyMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpWeeklyMain)
            binding.rvHotMain.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(
                    this@MainActivity,
                    3,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = DrinkingGameMainAdapter(this@MainActivity, hotWeeklyGameData)
            }
        }
        binding.cpDailyMain.setOnClickListener { v ->
            ensureOneSelected(binding.cpDailyMain)
            binding.rvHotMain.apply {
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(
                    this@MainActivity,
                    3,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
                adapter = DrinkingGameMainAdapter(this@MainActivity, hotDailyGameData)
            }
        }
    }

    private fun userRankingRecyclerMain() {
        binding.rvUserRankingMain.adapter = UserRankingMainAdapter(userRankingData)
        binding.rvUserRankingMain.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    /***************************************************************************************************
     * 리사이클러뷰 소스코드 끝
     ****************************************************************************************************/


    /***************************************************************************************************
     * API 소스코드 시작
     ****************************************************************************************************/

    private fun getAllData(){
        RetrofitClient.homeApiService.getHomeData("test").enqueue(object :
            Callback<HomeResponse> {
            override fun onResponse(
                call: Call<HomeResponse>,
                response: Response<HomeResponse>
            ) {
                if (response.isSuccessful) {
                    val latestIntros = response.body()?.latestIntros
                    val latestCreationGames = response.body()?.latestCreationGames
                    val officialGamesByLikes = response.body()?.officialGamesByLikes
                    val creationGamesByDailyScore = response.body()?.creationGamesByDailyScore
                    val introsByDailyScore = response.body()?.introsByDailyScore
                    val officialGamesByDailyScore = response.body()?.officialGamesByDailyScore
                    val introsByLikes = response.body()?.introsByLikes
                    val introsByViews = response.body()?.introsByViews
                    val gamesByWeeklyScore = response.body()?.gamesByWeeklyScore

                    Log.d("술겜위키", "latestIntroData : ${latestIntros}")
                    Log.d("술겜위키", "latestCreationGames : ${latestCreationGames}")
                    Log.d("술겜위키", "officialGamesByLikes : ${officialGamesByLikes}")
                    Log.d("술겜위키", "creationGamesByDailyScore : ${creationGamesByDailyScore}")
                    Log.d("술겜위키", "introsByDailyScore : ${introsByDailyScore}")
                    Log.d("술겜위키", "officialGamesByDailyScore : ${officialGamesByDailyScore}")
                    Log.d("술겜위키", "introsByLikes : ${introsByLikes}")
                    Log.d("술겜위키", "introsByViews : ${introsByViews}")
                    Log.d("술겜위키", "gamesByWeeklyScore : ${gamesByWeeklyScore}")

                    // 값 넣어주기
                    // 최신 게시물(창작)
                    if(latestCreationGames != null){
                        for(i in latestCreationGames.indices)
                            latestFeedMainItemCreationData.add(LatestFeedMainItem(latestCreationGames.get(i).thumbnailIcon, latestCreationGames.get(i).title, latestCreationGames.get(i).member.profileUrl, latestCreationGames.get(i).member.nickname, latestCreationGames.get(i).likes))
                    }
                    // 최신 게시물(인트로)
                    if (latestIntros != null) {
                        for(i in latestIntros.indices)
                            latestFeedMainItemIntroData.add(LatestFeedMainItem(latestIntros.get(i).thumbnailIcon, latestIntros.get(i).title, latestIntros.get(i).member.profileUrl, latestIntros.get(i).member.nickname, latestIntros.get(i).likes))
                    }
                    // 국룰 술게임
                    if(officialGamesByLikes != null){
                        for(i in officialGamesByLikes.indices)
                            officialGameData.add(DrinkingGameMainItem(officialGamesByLikes.get(i).thumbnailIcon, officialGamesByLikes.get(i).title, officialGamesByLikes.get(i).description, officialGamesByLikes.get(i).likes))
                    }
                    // 실시간 ㅅㄱㅇㅋ 차트(창작)
                    if(creationGamesByDailyScore != null){
                        for(i in creationGamesByDailyScore.indices)
                            liveChartMainItemCreationData.add(LiveChartMainItem(creationGamesByDailyScore.get(i).thumbnailIcon, creationGamesByDailyScore.get(i).title, creationGamesByDailyScore.get(i).description, creationGamesByDailyScore.get(i).likes))
                    }
                    // 실시간 ㅅㄱㅇㅋ 차트(인트로)
                    if(introsByDailyScore != null){
                        for(i in introsByDailyScore.indices)
                            liveChartMainItemIntroData.add(LiveChartMainItem(introsByDailyScore.get(i).thumbnailIcon, introsByDailyScore.get(i).title, introsByDailyScore.get(i).description, introsByDailyScore.get(i).likes))
                    }
                    // 실시간 ㅅㄱㅇㅋ 차트(국룰)
                    if(officialGamesByDailyScore != null){
                        for(i in officialGamesByDailyScore.indices)
                            liveChartMainItemOfficialData.add(LiveChartMainItem(officialGamesByDailyScore.get(i).thumbnailIcon, officialGamesByDailyScore.get(i).title, officialGamesByDailyScore.get(i).description, officialGamesByDailyScore.get(i).likes))
                    }
                    // 인트로 자랑하기(최신순)
                    if(latestIntros != null){
                        for(i in latestIntros.indices)
                            shareIntroRecentData.add(IntroMainItem(latestIntros.get(i).title, latestIntros.get(i).description, latestIntros.get(i).member.nickname, latestIntros.get(i).likes))
                    }
                    // 인트로 자랑하기(좋아요순)
                    if(introsByLikes != null){
                        for(i in introsByLikes.indices)
                            shareIntroLikesData.add(IntroMainItem(introsByLikes.get(i).title, introsByLikes.get(i).description, introsByLikes.get(i).member.nickname, introsByLikes.get(i).likes))
                    }
                    // 인트로 자랑하기(조회수순)
                    if(introsByViews != null){
                        for(i in introsByViews.indices)
                            shareIntroViewData.add(IntroMainItem(introsByViews.get(i).title, introsByViews.get(i).description, introsByViews.get(i).member.nickname, introsByViews.get(i).likes))
                    }
                    // 요즘 핫한 술게임(주간)
                    if(gamesByWeeklyScore != null){
                        for(i in gamesByWeeklyScore.indices)
                            hotWeeklyGameData.add(DrinkingGameMainItem(gamesByWeeklyScore.get(i).thumbnailIcon, gamesByWeeklyScore.get(i).title, gamesByWeeklyScore.get(i).description, gamesByWeeklyScore.get(i).likes))
                    }
                    // 요즘 핫한 술게임(일간)
                    if (officialGamesByDailyScore != null && creationGamesByDailyScore != null) {
                        val combinedList = ArrayList<DrinkingGameMainItem>()

                        // officialGamesByDailyScore를 DrinkingGameMainItem으로 매핑
                        combinedList.addAll(officialGamesByDailyScore.map {
                            DrinkingGameMainItem(it.thumbnailIcon, it.title, it.description, it.likes)
                        })

                        // creationGamesByDailyScore를 DrinkingGameMainItem으로 매핑
                        combinedList.addAll(creationGamesByDailyScore.map {
                            DrinkingGameMainItem(it.thumbnailIcon, it.title, it.description, it.likes)
                        })

                        // score 값으로 내림차순 정렬하고 상위 9개 항목 추출
                        val top9Items = combinedList.sortedByDescending { it.cntHeart }.take(9)

                        // hotDailyGameData에 상위 9개 항목 넣기
                        hotDailyGameData.clear()  // 기존 데이터를 지우고
                        hotDailyGameData.addAll(top9Items)

                        // introsByViews 데이터를 hotDailyGameData에 추가
                        for (i in introsByViews?.indices!!) {
                            hotDailyGameData.add(
                                DrinkingGameMainItem(
                                    introsByViews?.get(i)?.thumbnailIcon,
                                    introsByViews?.get(i)?.title,
                                    introsByViews?.get(i)?.description,
                                    introsByViews?.get(i)?.likes
                                )
                            )
                        }
                    }

                    dismissLoadingDialog()
                    recyclerMain()
                } else {
                    Log.e("술겜위키", "응답 실패: ${response.code()} - ${response.errorBody()?.string()}")
                    dismissLoadingDialog()
                }
            }

            override fun onFailure(p0: Call<HomeResponse>, p1: Throwable) {
                Log.d("술겜위키", "Failure: ${p1.message}")
                dismissLoadingDialog()
            }
        })
    }

    private fun getUserRank(){
//        RetrofitClient.memberApiService.topRank(createRequestBody("0"), createRequestBody("10")).enqueue(object :
//            Callback<RankingHistoryPage> {
//            override fun onResponse(
//                call: Call<RankingHistoryPage>,
//                response: Response<RankingHistoryPage>
//            ) {
//                Log.d("술겜위키", "여기 들어옴")
//                if (response.isSuccessful) {
//
//                    Log.d("술겜위키", "response : ${response.body().}")
//
//                    val userTopRanking = response.body()
//
//                    // 값 넣어주기
//                    if(userTopRanking != null){
//                        for(i in userTopRanking.indices)
//                            userRankingData.add(UserRankingMainItem(gamesByWeeklyScore.get(i).thumbnailIcon, gamesByWeeklyScore.get(i).title, gamesByWeeklyScore.get(i).description, gamesByWeeklyScore.get(i).likes))
//                    }
//
//                } else {
//                    Log.e("술겜위키", "응답 실패: ${response.code()} - ${response.errorBody()?.string()}")
//                }
//            }
//            override fun onFailure(
//                p0: Call<RankingHistoryPage>,
//                p1: Throwable
//            ) {
//                Log.d("술겜위키", "Failure: ${p1.message}")
//            }
//        })
    }

    private fun createRequestBody(value: String): RequestBody {
        return RequestBody.create(MultipartBody.FORM, value)
    }

    // 로딩 창 표시
    private fun showLoadingDialog() {
        progressDialog.show()
    }

    // 로딩 창 닫기
    private fun dismissLoadingDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
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

        binding.searchviewMain.setOnClickListener {
            val intent = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intent)
        }

        behavior = BottomSheetBehavior.from(binding.bottomSheetMain)
        behavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    // 완전히 펼쳐졌을 때
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        if(!TokenUtil().getRefreshToken(this@MainActivity).isNullOrBlank()){
                            val intent = Intent(this@MainActivity, MyPageActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            startActivity(intent)
                        } else {
                            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                            Toast.makeText(this@MainActivity, "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show()
                        }
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
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.backgroundDimMain.alpha = slideOffset
            }
        })
    }

    override fun onResume() {
        super.onResume()
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun startFabTimer() {
        // 일정 시간 후 FloatingActionButton 숨김
        handler.postDelayed(hideFabRunnable, userInteractionTimeout)
    }

    private fun resetFabTimer() {
        // 타이머 리셋
        handler.removeCallbacks(hideFabRunnable)
        handler.postDelayed(hideFabRunnable, userInteractionTimeout)
        binding.expandableFab.show() // 다시 사용자가 입력하면 FAB 표시
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
        resetFabTimer() // 어떤 사용자 입력이 발생해도 타이머를 리셋
    }

    override fun onDestroy() {
        super.onDestroy()
        // 메모리 누수를 방지하기 위해 Handler를 해제
        handler.removeCallbacks(hideFabRunnable)
    }

}