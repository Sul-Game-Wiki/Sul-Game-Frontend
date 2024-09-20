package info.sul_game.activity

import android.annotation.SuppressLint
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
import info.sul_game.databinding.ActivityBookmarkedPostsBinding
import info.sul_game.databinding.ActivityLikedPostsBinding
import info.sul_game.ui.mypage.MyPagePostAdapter
import info.sul_game.ui.mypage.MyPagePostItem
import info.sul_game.utils.TokenUtil
import info.sul_game.viewmodel.MemberViewModel

class BookmarkedPostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBookmarkedPostsBinding

    private val memberViewModel: MemberViewModel by viewModels()

    private val TAG = "BOOKMARKEDPOST"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkedPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerBookmark()

        binding.btnCloseBookmarkedpost.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun recyclerBookmark(){
        val accessToken = TokenUtil().getAccessToken(this@BookmarkedPostsActivity)
        val allBookmarkedPosts = mutableListOf<BasePost>()
        val adapter = MyPagePostAdapter(allBookmarkedPosts)

        binding.recyclerviewBookmarkedpost.adapter = adapter
        binding.recyclerviewBookmarkedpost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewBookmarkedpost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        accessToken?.let {
            memberViewModel.getBookmarkedPosts("Bearer $accessToken")
            Log.d(TAG, "recyclerBookmark ($accessToken)")

            memberViewModel.bookmarkedPosts.observe(this) { memberResponse ->
                if (memberResponse != null) {
                    Log.d(TAG, "$memberResponse")
                    // null 체크 후 addAll 호출
                    memberResponse.bookmarkedIntroIds?.let { allBookmarkedPosts.addAll(it) }
                    memberResponse.bookmarkedCreationGameIds?.let { allBookmarkedPosts.addAll(it) }
                    memberResponse.bookmarkedOfficialGameIds?.let { allBookmarkedPosts.addAll(it) }

                    adapter.notifyDataSetChanged()

                } else {
                    Log.e(TAG,"memberResponse 데이터가 존재하지 않음")
                }
            }
        }


    }
}