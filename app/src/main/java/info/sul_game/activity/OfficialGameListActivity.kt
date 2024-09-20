package info.sul_game.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import info.sul_game.R
import info.sul_game.databinding.ActivityCreationGameDetailBinding
import info.sul_game.databinding.ActivityOfficialGameListBinding
import info.sul_game.model.OfficialGameListViewModel
import info.sul_game.model.RelatedSearchGameListViewModel
import info.sul_game.recyclerview.OfficialGameListAdapter
import info.sul_game.utils.Direction
import info.sul_game.utils.SortBy
import info.sul_game.utils.SourceType

class OfficialGameListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOfficialGameListBinding
    private lateinit var viewModel: OfficialGameListViewModel
//    private val imageAdapter by lazy { OfficialGameListAdapter(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOfficialGameListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        back()
//        viewModel = ViewModelProvider(this).get(OfficialGameListViewModel::class.java)
//        viewModel.loadGameList(0,10,SourceType.OFFICIAL_GAME , SortBy.CREATION_DATE,Direction.ASC)




    }
//    private fun setupRecyclerView() {
//
//        binding.rvOfficialList.apply {
//            adapter = imageAdapter
//
//            addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {         // 스크롤이 될 때
//                    super.onScrolled(recyclerView, dx, dy)
//                    val layoutManager = recyclerView.layoutManager as GridLayoutManager         // 그리드레이아웃 매니저로 설정
//                    val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
//                    //Returns the adapter position of the last fully visible view. This position does not include adapter changes that were dispatched after the last layout pass
//
//                    val totalItemCount = layoutManager.itemCount
//                    if (lastVisibleItemPosition == totalItemCount - 1) {        // 마지막으로 보여진 뷰의 position와 전채 리스트의 개수 - 1 (인덱스는 0,1,2 숫자는 1,2,3)
//                        loadMoreImages()
//                    }
//                }
//            })
//        }
//    }
//    private fun loadMoreImages() {
//        if (!isEnd) {               // 마지막 페이지 여부 flag확인하고 flag에 맞춰서 코드 실행
//            searchImages(SearchType.PAGING)
//        } else {
//            showToast("마지막 페이지")
//        }
//    }
//    override fun onItemClick(document: Documents) {          // adapter에서 override해온 onItemClick이 싱행될때
//        val intent = Intent(this, DetailActivity::class.java).apply {
//            putExtra("IMAGE_DATA", document)           // 상수는 const를 사용해서 선언하는게 안전하긴 합니다.
//        }
//        startActivity(intent)
//    }



    private fun back(){
        binding.btnBackOfficialList.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}