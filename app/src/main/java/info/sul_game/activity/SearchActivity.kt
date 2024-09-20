package info.sul_game.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import info.sul_game.databinding.ActivitySearchBinding
import info.sul_game.recyclerview.LatestSearchAdapter
import info.sul_game.utils.view.CustomSearchView

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    private var searchHistory: MutableList<String> = mutableListOf()
    private lateinit var adapter: LatestSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSearchView() // searchview 초기화
        initRecyclerView() // recyclerview 초기화

        binding.containerLatestSearch.visibility = View.VISIBLE
        binding.containerResultSearch.visibility = View.GONE
    }
    
    private fun initRecyclerView() {
//        var recentSearchList = mutableListOf("바니바니", "고래 게임")
        // 저장된 검색어 불러오기
        searchHistory = loadSearchHistory()

        adapter = LatestSearchAdapter(searchHistory) {
                query: String ->  binding.searchviewSearch.setQuery(query, false)
        }

        binding.recyclerviewRecentSearch.adapter = adapter
        binding.recyclerviewRecentSearch.layoutManager = LinearLayoutManager(this)


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
                }
                return true
            }

            // 검색을 제출했을 때 호출
            override fun onQueryTextSubmit(query: String): Boolean {
                addSearchQuery(query)
                saveSearchHistory(searchHistory) // 검색어 저장
                Log.d("Search", "onQueryTextSubmit: ${query}")
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
        if (searchHistory.size > 10) {
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