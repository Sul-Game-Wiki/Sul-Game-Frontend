package info.sul_game.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.sul_game.databinding.ActivitySearchBinding
import info.sul_game.recyclerview.LatestSearchAdapter
import info.sul_game.recyclerview.SearchAdapter
import info.sul_game.utils.view.CustomSearchView
import info.sul_game.viewmodel.SearchViewModel

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private var searchHistory: MutableList<String> = mutableListOf()
    private lateinit var adapter: LatestSearchAdapter
    private lateinit var introAdapter: SearchAdapter // Intro RecyclerView 어댑터
    private lateinit var officialGameAdapter: SearchAdapter // OfficialGame RecyclerView 어댑터
    private lateinit var creationGameAdapter: SearchAdapter // CreationGame RecyclerView 어댑터

    private val searchViewModel : SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearchView() // searchview 초기화
        initRecyclerView() // recyclerview 초기화
        initHideButton() //

        binding.containerLatestSearch.visibility = View.VISIBLE
        binding.containerResultSearch.visibility = View.GONE
    }

    private fun initHideButton() {
        TODO("Not yet implemented")
    }

    private fun initRecyclerView() {
//        var recentSearchList = mutableListOf("바니바니", "고래 게임")
        // 저장된 검색어 불러오기
        searchHistory = loadSearchHistory()
        Log.d("Search", "$searchHistory")

        adapter = LatestSearchAdapter(searchHistory) {
                query: String ->  binding.searchviewSearch.setQuery(query, false)
        }

        // RecyclerView 초기화
        introAdapter = SearchAdapter(mutableListOf()) // 초기화
        officialGameAdapter = SearchAdapter(mutableListOf()) // 초기화
        creationGameAdapter = SearchAdapter(mutableListOf()) // 초기화

        binding.recyclerviewRecentSearch.adapter = adapter
        binding.recyclerviewRecentSearch.layoutManager = LinearLayoutManager(this)

        binding.rvOfficialGameSearch.adapter = officialGameAdapter
        binding.rvOfficialGameSearch.layoutManager = LinearLayoutManager(this)
        binding.rvOfficialGameSearch.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        binding.rvCreationGameSearch.adapter = creationGameAdapter
        binding.rvCreationGameSearch.layoutManager = LinearLayoutManager(this)
        binding.rvCreationGameSearch.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        binding.rvIntroGameSearch.adapter = introAdapter
        binding.rvIntroGameSearch.layoutManager = LinearLayoutManager(this)
        binding.rvIntroGameSearch.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        // 검색 결과 관찰
        searchViewModel.searchResults.observe(this) { results ->
            introAdapter.updateItems(results.intros)
            officialGameAdapter.updateItems(results.officialGames)
            creationGameAdapter.updateItems(results.creationGames)
        }
    }

    private fun initSearchView(){
        // searchView의 뒤로가기 버튼 기능 구현
        binding.searchviewSearch.setOnBackListener {
            finish()
        }

        // searchView의 setOnQueryTextListener 설정
        binding.searchviewSearch.setOnQueryTextListener(object : CustomSearchView.OnQueryTextListener{
           // 텍스트가 변경될 때 호출
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    binding.containerLatestSearch.visibility = View.VISIBLE
                    binding.containerResultSearch.visibility = View.GONE
                } else {
                    binding.containerLatestSearch.visibility = View.GONE
                    binding.containerResultSearch.visibility = View.VISIBLE

                    searchViewModel.searchPosts(query = newText)
                }
                return true
            }

            // 검색을 제출했을 때 호출
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.isNotBlank()){
                    addSearchQuery(query)
                    saveSearchHistory(searchHistory) // 검색어 저장
                    Log.d("Search", "onQueryTextSubmit: ${query}")
                } else {
                    Toast.makeText(this@SearchActivity, "검색창이 비어있습니다.", Toast.LENGTH_SHORT).show()
                }

                return true
            }

        } )
    }

    private fun addSearchQuery(query: String) {
        // 이미 존재하는 검색어 제거
        searchHistory.remove(query)

        // 검색어 추가
        searchHistory.add(0, query)



        // 최대 검색어 개수를 제한 (예: 최근 10개)
        if (searchHistory.size > 100) {
            searchHistory.removeLast()
        }

        adapter.updateItems(searchHistory)
    }

    private fun saveSearchHistory(searchHistory: List<String>) {
        val sharedPreferences = getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // 리스트를 JSON 문자열로 변환하여 저장
        val json = Gson().toJson(searchHistory)
        editor.putString("search_history", json)
        editor.apply()
    }


    private fun loadSearchHistory(): MutableList<String> {
        val sharedPreferences = getSharedPreferences("search_prefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("search_history", null)

        return if (json != null) {
            val type = object : TypeToken<MutableList<String>>() {}.type
            Gson().fromJson(json, type)
        } else {
            mutableListOf()
        }
    }



}