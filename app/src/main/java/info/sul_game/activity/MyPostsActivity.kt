package info.sul_game.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.sul_game.R
import info.sul_game.data.source.remote.BasePost
import info.sul_game.databinding.ActivityMyPostsBinding
import info.sul_game.ui.mypage.MyPagePostAdapter
import info.sul_game.ui.mypage.MyPagePostItem
import info.sul_game.utils.TokenUtil
import info.sul_game.viewmodel.MemberViewModel

class MyPostsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyPostsBinding
    private val memberViewModel: MemberViewModel by viewModels()

    private val TAG = "LIKEDPOST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerMyPost()
    }

    private fun recyclerMyPost(){
        val accessToken = TokenUtil().getAccessToken(this@MyPostsActivity)
        val allLikedPosts = mutableListOf<BasePost>()

        accessToken?.let {
            memberViewModel.getMyPosts("Bearer $accessToken")
            Log.d(TAG, "updateMyPageUiWithData: getLikedPosts ($accessToken)")

            memberViewModel.likedPosts.observe(this) { memberResponse ->
                if (memberResponse != null) {
                    Log.d(TAG, "$memberResponse")

                } else {
                    Log.e(TAG,"memberResponse 데이터가 존재하지 않음")
                }
            }
        }

        binding.recyclerviewMypost.adapter = MyPagePostAdapter(allLikedPosts)
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewMypost.addItemDecoration(DividerItemDecoration(this, VERTICAL))
    }
}