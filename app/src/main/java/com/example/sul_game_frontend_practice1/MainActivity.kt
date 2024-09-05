package com.example.sul_game_frontend_practice1

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.bumptech.glide.Glide
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding
import com.example.sul_game_frontend_practice1.mypage.LikedPost
import com.example.sul_game_frontend_practice1.mypage.LikedPostAdapter
import com.example.sul_game_frontend_practice1.mypage.MyPost
import com.example.sul_game_frontend_practice1.mypage.MyPostAdapter
import com.example.sul_game_frontend_practice1.retrofit.NetworkManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sul_game_frontend_practice1.mypage.BookmarkedPost
import com.example.sul_game_frontend_practice1.mypage.BookmarkedPostAdapter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    // BottomSheet가 완전히 펼쳐진 상태인지 확인할 수 있는 변수
    private var isBottomSheetExpanded : Boolean = false

    /* 현재 열려있는 BottomSheet의 Child를 확인할 수 있는 변수
    0 : 마이페이지, 1 : 내게시글, 2 : 즐겨찾기게시글, 3 : 좋아요 게시글, 4: 계정 설정*/
    private var displayedChildBottomSheet : Int = 0
    private companion object {
        const val MYPAGE = 0
        const val MYPOST = 1
        const val BOOKMARKEDPOST = 2
        const val LIKEDPROFILE = 3
        const val PROFILEEDIT = 4

        const val REQUEST_CAMERA_PERMISSION = 100
        const val REQUEST_MEDIA_PERMISSION = 101
        const val REQUEST_IMAGE_CAPTURE = 102
        const val REQUEST_IMAGE_GALLERY = 103
    }

    private var newProfileImage : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NetworkManager.getProfile(this)
        updateUIWithMemberData()

        initEvent()
        persistentBottomSheetEvent()

    }

    // 화면 초기화 시 호출되는 함수
    private fun initEvent() {
        requestPermissions()

        isBottomSheetExpanded = false
        setDisplayedChildBottomSheet(MYPAGE)

        binding.searchviewMain.setOnClickListener {
            if (!isBottomSheetExpanded) {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }

    }

    // 화면 초기화 시 호출되는 bottomSheet에서의 이벤트 처리를 위한 함수
    private fun persistentBottomSheetEvent() {
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
                    BottomSheetBehavior.STATE_DRAGGING ->
                        binding.backgroundDimMain.visibility = View.VISIBLE

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

        binding.tvEditprofileEditprofile.setOnClickListener {
            showImagePickerDialog()
        }

        // 프로필 수정 확인 버튼
        binding.btnSumbitEditprofile.setOnClickListener {
            NetworkManager.updateNickname(binding.et1Editprofile.text.toString(), this)
            NetworkManager.getProfile( this)
            newProfileImage?.let {
                NetworkManager.updateProfileImage(it, this)
            }

            updateUIWithMemberData()
            setDisplayedChildBottomSheet(MYPAGE)
        }

        binding.btnMypostMypage.setOnClickListener { setDisplayedChildBottomSheet(MYPOST) }
        binding.btnBookmarkMypage.setOnClickListener { setDisplayedChildBottomSheet(BOOKMARKEDPOST) }
        binding.btnLikedpostMypage.setOnClickListener { setDisplayedChildBottomSheet(LIKEDPROFILE) }
        binding.btnProfileSettingMypage.setOnClickListener { setDisplayedChildBottomSheet(PROFILEEDIT) }
    }

    private fun updateUIWithMemberData() {
        val app = application as MyApplication
        val member = app.member
        val memberContentInteraction = app.memberContentInteraction

        member?.let {
            // 마이 페이지 처리
            binding.tvUsernameMypage.text = member.nickname
            binding.tvUniversityMypage.text = member.college

            val defaultImage = R.color.light_gray
            Glide.with(this)
                .load(member.profileUrl) // 불러올 이미지 url
                .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .circleCrop() // 동그랗게 자르기
                .into(binding.civProfileMypage) // 이미지를 넣을 뷰

            // 프로필 편집 처리
            binding.et1Editprofile.setText(member.nickname)
            binding.et2Editprofile.setText(member.email)
            binding.et3Editprofile.setText(member.birthDate)
            binding.et4Editprofile.setText(member.college)

            // 내 정보
            binding.tvExpMypage.text = "${member.exp}포인트"
            binding.progressbarExpMypage.progress = member.exp
        }

        // 내 게시글 처리
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val myPostList = mutableListOf<MyPost>()
        val likedPostList = mutableListOf<LikedPost>()
        val bookmarkedPostList = mutableListOf<BookmarkedPost>()

        // 목업 데이터 추가
        myPostList.add(MyPost("어목조동", "자연과 함께하는 술게임"))
        myPostList.add(MyPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임"))

        likedPostList.add(LikedPost("어목조동", "자연과 함께하는 술게임", "구해조"))
        likedPostList.add(LikedPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임", "구해조"))

        bookmarkedPostList.add(BookmarkedPost("어목조동", "자연과 함께하는 술게임", "구해조"))
        bookmarkedPostList.add(BookmarkedPost("딸기당근수박참외 찍고", "지목이 더해진 과일게임", "구해조"))

        binding.recyclerviewMypost.adapter = MyPostAdapter(myPostList)
        binding.recyclerviewMypost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewMypost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        binding.recyclerviewLikedpost.adapter = LikedPostAdapter(likedPostList)
        binding.recyclerviewLikedpost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewLikedpost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

        binding.recyclerviewBookmarkedpost.adapter = BookmarkedPostAdapter(bookmarkedPostList)
        binding.recyclerviewBookmarkedpost.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewBookmarkedpost.addItemDecoration(DividerItemDecoration(this, VERTICAL))

    }

    // 권한 요청 함수
    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13(API 33) 이상에서는 READ_MEDIA_IMAGES 사용
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_MEDIA_PERMISSION)
            }
        } else {
            // Android 12(API 32) 이하에서는 READ_EXTERNAL_STORAGE 사용
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_MEDIA_PERMISSION)
            }
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "카메라 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "카메라 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            REQUEST_MEDIA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "미디어 접근 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "미디어 접근 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    // onActivityResult로 카메라나 갤러리에서 선택한 이미지 처리
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    // 이미지를 서버로 전송하는 메서드 호출
                    updateProfileImage(convertBitmapToFile(imageBitmap))
                }
                REQUEST_IMAGE_GALLERY -> {
                    val imageUri = data?.data
                    val imageFile = File(getRealPathFromURI(imageUri!!))
                    // 이미지를 서버로 전송하는 메서드 호출
                    updateProfileImage(imageFile)
                }
            }
        }
    }

    private fun updateProfileImage(imageFile : File){
        newProfileImage = imageFile

        NetworkManager.updateProfileImage(imageFile, this)

    }

    // URI로부터 실제 파일 경로 가져오기
    private fun getRealPathFromURI(uri: Uri): String {
        var filePath: String? = null
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            it.moveToFirst()
            val index = it.getColumnIndex(MediaStore.Images.Media.DATA)
            filePath = it.getString(index)
            it.close()
        }
        return filePath!!
    }

    // 비트맵을 파일로 변환하는 함수
    private fun convertBitmapToFile(bitmap: Bitmap): File {
        val file = File(cacheDir, "image_${System.currentTimeMillis()}.jpg")
        file.createNewFile()

        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitmapData)
        fos.flush()
        fos.close()

        return file
    }

    private fun showImagePickerDialog() {
        val options = arrayOf("카메라로 사진 찍기", "갤러리에서 선택")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("사진 선택")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openCamera() // 카메라 선택
                    1 -> openGallery() // 갤러리 선택
                }
            }
        builder.show()
    }

    // BottomSheet의 child 설정
    private fun setDisplayedChildBottomSheet(child: Int){
        val bottomSheetTitle = arrayListOf<String>("마이 페이지", "내 게시글", "즐겨찾기 게시글", "좋아요 게시글", "계정 설정")

        binding.viewflipperBottomsheet.displayedChild = child
        binding.tvTitleMypage.text = bottomSheetTitle[child]
        displayedChildBottomSheet = child
    }

    // 뒤로가기 버튼 처리
    override fun onBackPressed() {
        if(isBottomSheetExpanded && displayedChildBottomSheet > MYPAGE){
            setDisplayedChildBottomSheet(0)
        }
        else{
            super.onBackPressed()
        }
    }

}