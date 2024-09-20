package info.sul_game.utils.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import info.sul_game.R
import info.sul_game.databinding.DialogProfileeditBinding
import info.sul_game.utils.TokenUtil
import info.sul_game.viewmodel.MemberViewModel
import android.net.Uri

class ProfileImageModalDialog : BottomSheetDialogFragment() {
    private lateinit var binding: DialogProfileeditBinding
    private val viewModel: MemberViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MemberViewModel::class.java)
    }

    private val images = listOf(
        listOf(R.drawable.ic_profile_icon_01, R.drawable.ic_profile_icon_02, R.drawable.ic_profile_icon_03, R.drawable.ic_profile_icon_04, R.drawable.ic_profile_icon_05, R.drawable.ic_profile_icon_06, R.drawable.ic_profile_icon_07, R.drawable.ic_profile_icon_08),
        listOf(R.drawable.ic_profile_icon_09)
    )

    // Drawable ID와 SVG 파일 이름 매핑
    private val drawableToSvgMap = mapOf(
        R.drawable.ic_profile_icon_01 to "ic_profile_icon_01.svg",
        R.drawable.ic_profile_icon_02 to "ic_profile_icon_02.svg",
        R.drawable.ic_profile_icon_03 to "ic_profile_icon_03.svg",
        R.drawable.ic_profile_icon_04 to "ic_profile_icon_04.svg",
        R.drawable.ic_profile_icon_05 to "ic_profile_icon_05.svg",
        R.drawable.ic_profile_icon_06 to "ic_profile_icon_06.svg",
        R.drawable.ic_profile_icon_07 to "ic_profile_icon_07.svg",
        R.drawable.ic_profile_icon_08 to "ic_profile_icon_08.svg",
        R.drawable.ic_profile_icon_09 to "ic_profile_icon_09.svg"
    )

    private var selectedImageUri: Uri? = null
    private var selectedImageSvg: String? = null


    companion object {
        private const val REQUEST_PERMISSIONS = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val accessToken = TokenUtil().getAccessToken(requireContext())

        binding = DialogProfileeditBinding.inflate(inflater, container, false)

        viewModel.getMemberProfile("Bearer $accessToken")

        viewModel.memberResponse.observe(viewLifecycleOwner) { memberResponse ->
            val defaultImage = R.color.light_gray
            Glide.with(this)
                .load(memberResponse.member.profileUrl)
                .placeholder(defaultImage)
                .error(defaultImage)
                .fallback(defaultImage)
                .into(binding.civProfileProfileditDialog)
        }

        // Setup ViewPager2 with TabLayout
        val adapter = ImagePagerAdapter(requireActivity(), images) { imageResId ->
            selectedImageSvg = drawableToSvgMap[imageResId]
            binding.civProfileProfileditDialog.setImageResource(imageResId)
        }
        binding.viewpagerProfileditDialog.adapter = adapter

        TabLayoutMediator(binding.tabLayoutProfileditDialog, binding.viewpagerProfileditDialog) { tab, position ->}.attach()

        binding.btnCameraProfileditDialog.setOnClickListener {
            Toast.makeText(requireContext(), "추후에 업데이트 예정입니다.", Toast.LENGTH_SHORT).show()
        }

        binding.btnSaveProfileditDialog.setOnClickListener {
            Toast.makeText(requireContext(), "추후에 업데이트 예정입니다.", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return binding.root
    }

}
