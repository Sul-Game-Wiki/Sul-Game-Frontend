package info.sul_game.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.sul_game.databinding.ActivityLikedPostsBinding
import info.sul_game.ui.mypage.MyPagePostAdapter
import info.sul_game.ui.mypage.MyPagePostItem

class LikedPostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikedPostsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerLike()
    }

    private fun recyclerLike(){
        val mockPostList = mutableListOf<MyPagePostItem>()

        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))
        mockPostList.add(MyPagePostItem("00:00", 0, "바니바니", "하늘에서 토끼가 내려온다고?", 100, 100, "구해조", "", false))


        binding.recyclerviewLikedpost.adapter = MyPagePostAdapter(mockPostList)
        binding.recyclerviewLikedpost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewLikedpost.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }
}