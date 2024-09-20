package info.sul_game.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import info.sul_game.data.source.remote.BasePost
import info.sul_game.databinding.ActivityLikedPostsBinding
import info.sul_game.ui.mypage.MyPagePostAdapter
import info.sul_game.utils.TokenUtil
import info.sul_game.viewmodel.MemberViewModel

class LikedPostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikedPostsBinding
    private val memberViewModel: MemberViewModel by viewModels()

    private val TAG = "LIKEDPOST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerLike()
        binding.btnCloseLikedpost.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerLike(){
        val accessToken = TokenUtil().getAccessToken(this@LikedPostsActivity)
        val allLikedPosts = mutableListOf<BasePost>()
        val adapter = MyPagePostAdapter(allLikedPosts)

        binding.recyclerviewLikedpost.adapter = adapter
        binding.recyclerviewLikedpost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewLikedpost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        accessToken?.let {
            memberViewModel.getLikedPosts("Bearer $accessToken")
            Log.d(TAG, "updateMyPageUiWithData: getLikedPosts ($accessToken)")

            memberViewModel.likedPosts.observe(this) { memberResponse ->
                if (memberResponse != null) {
                    Log.d(TAG, "$memberResponse")
                    // null 체크 후 addAll 호출
                    memberResponse.likedOfficalGames?.let { allLikedPosts.addAll(it) }
                    memberResponse.likedCreationGames?.let { allLikedPosts.addAll(it) }
                    memberResponse.likedIntros?.let { allLikedPosts.addAll(it) }

                    adapter.notifyDataSetChanged()
                } else {
                    Log.e(TAG,"memberResponse 데이터가 존재하지 않음")
                }
            }
        }
    }

}