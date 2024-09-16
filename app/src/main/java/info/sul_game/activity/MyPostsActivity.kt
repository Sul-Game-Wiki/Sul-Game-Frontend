package info.sul_game.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.sul_game.R
import info.sul_game.databinding.ActivityMyPostsBinding
import info.sul_game.ui.mypage.MyPagePostAdapter
import info.sul_game.ui.mypage.MyPagePostItem

class MyPostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerMyPost()
    }

    private fun recyclerMyPost(){
        val mockPostList = mutableListOf<MyPagePostItem>()


        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", true))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", true))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))

        binding.recyclerviewMypost.adapter = MyPagePostAdapter(mockPostList)
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewMypost.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }
}