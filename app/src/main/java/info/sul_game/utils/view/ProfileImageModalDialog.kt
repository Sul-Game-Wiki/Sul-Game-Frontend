package info.sul_game.utils.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import info.sul_game.R
import info.sul_game.activity.MyPageActivity
import info.sul_game.config.RetrofitClient
import info.sul_game.databinding.DialogProfileeditBinding
import info.sul_game.model.Member
import info.sul_game.utils.TokenUtil
import info.sul_game.viewmodel.MemberViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ProfileImageModalDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogProfileeditBinding
    private val viewModel: MemberViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MemberViewModel::class.java)
    }

    private val images = listOf(
        listOf(R.drawable.ic_profile_icon_01, R.drawable.ic_profile_icon_02, R.drawable.ic_profile_icon_03, R.drawable.ic_profile_icon_04, R.drawable.ic_profile_icon_05, R.drawable.ic_profile_icon_06, R.drawable.ic_profile_icon_07, R.drawable.ic_profile_icon_08),
        listOf(R.drawable.ic_profile_icon_09)
    )
    private var selectedImage: Int? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accessToken = TokenUtil().getAccessToken(requireContext())

        binding = DialogProfileeditBinding.inflate(inflater, container, false)

        viewModel.getMemberProfile("Bearer $accessToken")

        viewModel.memberResponse.observe(this) { memberResponse ->
            memberResponse.member.profileUrl
            val defaultImage = R.color.light_gray
            Glide.with(this)
                .load(memberResponse.member.profileUrl) // 불러올 이미지 url
                .placeholder(defaultImage) // 이미지 로딩 시작하기 전 표시할 이미지
                .error(defaultImage) // 로딩 에러 발생 시 표시할 이미지
                .fallback(defaultImage) // 로드할 url 이 비어있을(null 등) 경우 표시할 이미지
                .into(binding.civProfileProfileditDialog) // 이미지를 넣을 뷰
        }

        // Setup ViewPager2 with TabLayout
        val adapter = ImagePagerAdapter(requireActivity(), images) { imageRes ->
            selectedImage = imageRes
            binding.civProfileProfileditDialog.setImageResource(selectedImage!!)
        }
        binding.viewpagerProfileditDialog.adapter = adapter

        // Setup TabLayout with ViewPager2
        TabLayoutMediator(binding.tabLayoutProfileditDialog, binding.viewpagerProfileditDialog) { tab, position ->}.attach()

        // Save button click listener
        binding.btnSaveProfileditDialog.setOnClickListener {
            selectedImage?.let {
                updateProfileImage("Bearer $accessToken", selectedImage!!)
                dismiss()
            } ?: run {
                Toast.makeText(context, "이미지를 선택해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root

    }

    private fun updateProfileImage(token: String, drawableId: Int) {
        val filePart = getMultipartFileFromDrawable(requireContext(), drawableId)

        RetrofitClient.memberApiService
            .updateProfileImage(token, filePart!!).enqueue(object :
                Callback<Member> {
                override fun onResponse(
                    call: Call<Member>,
                    response: Response<Member>
                ) {
                    if (response.isSuccessful) {
                        // 성공적으로 프로필 이미지 업데이트됨
                        val updatedMember = response.body()
                        Log.d("API", "onResponse: $updatedMember")
                        (activity as? MyPageActivity)?.updateMyPageUiWithData()

                    } else {
                        // API 호출 실패 처리
                        Log.e("API", "onResponse: ${response.code()}")
                    }
                }

                override fun onFailure(
                    p0: Call<Member>,
                    p1: Throwable
                ) {
                    Log.e("API", "onResponse: ${p1.message}")

                }

            })
    }

    private fun getBitmapFromDrawable(context: Context, drawableId: Int): Bitmap? {
        val drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)

        return drawable?.let {
            if (it is BitmapDrawable) {
                // BitmapDrawable의 경우 바로 Bitmap을 반환
                it.bitmap
            } else if (it is VectorDrawable) {
                // VectorDrawable을 Bitmap으로 변환
                val bitmap = Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                it.setBounds(0, 0, canvas.width, canvas.height)
                it.draw(canvas)
                bitmap
            } else {
                // 다른 타입의 Drawable에 대한 처리
                null
            }
        }
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
        val file = File(context.cacheDir, "profile_image.png")
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return file
    }

    private fun getMultipartFileFromDrawable(context: Context, drawableId: Int): MultipartBody.Part? {
        val bitmap = getBitmapFromDrawable(context, drawableId) ?: return null
        val file = saveBitmapToFile(context, bitmap)

        val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

    private fun File.asRequestBody(contentType: MediaType?): RequestBody {
        return RequestBody.create(contentType, this)
    }



}