// CreateCreationGameFragment.kt
package info.sul_game.fragment

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import info.sul_game.R
import info.sul_game.config.RetrofitClient
import info.sul_game.databinding.FragmentCreateCreateBinding
import info.sul_game.model.CreationCreateRepository
import info.sul_game.model.CreationCreateViewModelFactory
import info.sul_game.model.RelatedSearchGameListViewModel
import info.sul_game.recyclerview.CreateFileAdapter
import info.sul_game.recyclerview.RelatedGameListAdapter
import info.sul_game.recyclerview.RelatedGameListItem
import info.sul_game.utils.*
import info.sul_game.utils.FileUtil.createAudioFile
import info.sul_game.utils.FileUtil.createImageFile
import info.sul_game.utils.FileUtil.createVideoFile
import info.sul_game.viewmodel.CreationCreateViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

class CreateCreationGameFragment : Fragment() {
    companion object {
        private const val MAXIMUM_CHIP_COUNT = 4
    }
    private lateinit var relatedGameListAdapter: RelatedGameListAdapter
    private lateinit var relatedSearchGameListViewModel: RelatedSearchGameListViewModel
    private var fullGameList: List<RelatedGameListItem> = emptyList()
    private var introTags: List<String> = emptyList()
    private var _binding: FragmentCreateCreateBinding? = null
    private val binding get() = _binding!!
    private val selectedChipsCreate = mutableListOf<Chip>()
    private lateinit var gameCreateAdapter: CreateFileAdapter
    private lateinit var introCreateAdapter: CreateFileAdapter
    private lateinit var photoUri: Uri
    private lateinit var videoUri: Uri
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var videoLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private lateinit var audioLauncher: ActivityResultLauncher<Intent>
    private lateinit var audiofileLauncher: ActivityResultLauncher<Intent>
    private var mediaRecorder: MediaRecorder? = null
    private var audioFilePath: String? = null
    private var isIntroMode = false
    private var allData: List<RelatedGameListItem> = listOf()
    private lateinit var recordButton: ImageButton
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var relatedSearchGameListviewModel: RelatedSearchGameListViewModel
    private lateinit var viewModel: CreationCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val creationCreateApi = RetrofitClient.creationCreateSerive // 실제 인터페이스 이름 확인
        val creationCreateRepository = CreationCreateRepository(creationCreateApi,requireContext())

        // ViewModelFactory 초기화
        val factory = CreationCreateViewModelFactory(creationCreateRepository)

        // ViewModel 초기화
        viewModel = ViewModelProvider(this, factory).get(CreationCreateViewModel::class.java)

        // RelatedSearchGameListViewModel 초기화
        relatedGameListAdapter = RelatedGameListAdapter(arrayListOf()) { selectedGameTitle ->
            // 리사이클러뷰 아이템이 클릭되면 EditText에 title 설정
            binding.etSearchCreate.setText(selectedGameTitle)
            binding.rvCreateGameListCreate.visibility = View.GONE
        }


        // RecyclerView 어댑터 설정
        gameCreateAdapter = CreateFileAdapter(mutableListOf(), this)
        binding.rvMediaCreate.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMediaCreate.adapter = gameCreateAdapter

        introCreateAdapter = CreateFileAdapter(mutableListOf(), this)
        binding.rvIntroCreate.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvIntroCreate.adapter = introCreateAdapter


        binding.rvCreateGameListCreate.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvCreateGameListCreate.adapter = relatedGameListAdapter

        // 게임 리스트 로드


        // 권한 요청 및 미디어 선택 로직 설정
        setupMediaLaunchers()
        setupPermissionLauncher()

        // UI 요소 설정
        binding.rgIntroCreate.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btn_introexist_create -> binding.llIntroCreate.visibility = VISIBLE
                R.id.btn_intronothing_create -> binding.llIntroCreate.visibility = GONE
            }
        }

        binding.btnCameraCreate.setOnClickListener {
            PermissionUtil.requestCameraPermission(this, requestPermissionLauncher = requestPermissionLauncher) {
                showTutorialMediaSelectionDialog()
            }
        }

        binding.btnGalleryCreate.setOnClickListener {
            PermissionUtil.requestStoragePermission(this, requestPermissionLauncher = requestPermissionLauncher) {
                openGallery()
            }
        }

        binding.btnIntroVideoCreate.setOnClickListener {
            if (introCreateAdapter.isTotalExists()) {
                Toast.makeText(requireContext(), "인트로는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                isIntroMode = true
                PermissionUtil.requestCameraPermission(this, requestPermissionLauncher = requestPermissionLauncher) {
                    recordVideo()
                }
            }
        }

        recordButton = binding.btnIntroAudioCreate
        recordButton.setImageResource(R.drawable.btn_audio)
        recordButton.setOnClickListener {
            if (introCreateAdapter.isTotalExists()) {
                Toast.makeText(requireContext(), "인트로는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                PermissionUtil.requestAudioPermission(this, requestPermissionLauncher = requestPermissionLauncher) {
                    recordAudio()
                }
            }
        }

        binding.btnIntroFileCreate.setOnClickListener {
            PermissionUtil.requestStoragePermission(this, requestPermissionLauncher = requestPermissionLauncher) {
                showIntroMediaSelectionDialog()
            }
        }

        checkRecyclerViewVisibility()

        // ChipGroups와 Button을 가져옵니다.
        val chipGroups = listOf(
            binding.chipGroupLevelCreate,
            binding.chipGroupCountCreate,
            binding.chipGroupNoiseCreate,
            binding.chipGroupEtcCreate
        )

        // `chipGroupEtcCreate`에 대한 개별 설정 제거하고, ChipGroups 전체에 대해 설정
        binding.chipGroupEtcCreate.setOnCheckedChangeListener { group, checkedId ->
            val selectedChip = view.findViewById<Chip>(checkedId)
            val tag = GameTags(id = selectedChip.id.toString(), name = selectedChip.text.toString())
            val currentTags = viewModel.selectedTags.value?.toMutableList() ?: mutableListOf()

            if (selectedChip.isChecked) {
                if (currentTags.size >= MAXIMUM_CHIP_COUNT) {
                    Toast.makeText(requireContext(), "최대 $MAXIMUM_CHIP_COUNT 개까지만 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                    selectedChip.isChecked = false
                } else {
                    currentTags.add(tag)
                }
            } else {
                currentTags.remove(tag)
            }

            viewModel.updateSelectedTags(currentTags)
        }


        // ChipGroup의 선택 이벤트 리스너 설정
        for (group in chipGroups) {
            group.setOnCheckedChangeListener { _, _ -> checkSelectionOnChipChange(chipGroups) }
        }


        relatedSearchGameListViewModel = ViewModelProvider(this).get(RelatedSearchGameListViewModel::class.java)



        binding.rvCreateGameListCreate.adapter = relatedGameListAdapter

        // 게임 리스트를 로드하고 데이터를 처리하는 콜백
        relatedSearchGameListViewModel.loadGameList(null) { gameList ->
            fullGameList = gameList // 전체 리스트 저장
            relatedGameListAdapter.updateGameList(gameList) // 어댑터에 게임 리스트 업데이트
            binding.rvCreateGameListCreate.visibility = View.VISIBLE
        }

        // 검색 버튼 클릭 시 필터링
        binding.btnSearchCreate.setOnClickListener {
            val query = binding.etSearchCreate.text.toString().trim()
            val filteredGames = relatedSearchGameListViewModel.filterGameList(fullGameList, query)
            relatedGameListAdapter.updateGameList(filteredGames) // 필터링된 결과로 업데이트
            binding.rvCreateGameListCreate.visibility = View.VISIBLE
        }

        // LiveData 관찰 설정
        viewModel.responseMessage.observe(viewLifecycleOwner, { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        })

        // Button 클릭 이벤트 설정
        binding.btnEnrollCreate.setOnClickListener {
            // 선택된 Chip들을 직접 확인
            val selectedEtcChips = mutableListOf<Chip>()
            for (i in 0 until binding.chipGroupEtcCreate.childCount) {
                val chip = binding.chipGroupEtcCreate.getChildAt(i) as Chip
                if (chip.isChecked) {
                    selectedEtcChips.add(chip)
                }
            }

            // 선택된 Chip의 개수 검사
            if (selectedEtcChips.size == 0 || selectedEtcChips.size > MAXIMUM_CHIP_COUNT) {
                Toast.makeText(requireContext(), "태그를 올바르게 선택해주세요. 1개 이상, 최대 $MAXIMUM_CHIP_COUNT 개까지 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 선택된 Chip들의 태그 추출 (예: id나 text 등)
            val selectedTags = selectedEtcChips.map { chip->chip.tag.toString() }

            val gameUris: List<Uri> = gameCreateAdapter.getFiles() // List<Uri>
            val introUris: List<Uri> = introCreateAdapter.getFiles() // List<Uri>
            val imageUrls: List<String> = listOf<String>() // 필요 시 수정

            // Uri를 File로 변환
            val gameFiles: List<File> = gameUris.mapNotNull { it.toFile(requireContext()) }
            val introFiles: List<File> = introUris.mapNotNull { it.toFile(requireContext()) }

            // introMedia는 하나의 파일로 가정 (첫 번째 파일을 사용)
            val introMediaFile: File? = introFiles.firstOrNull()

            // multipartFiles는 나머지 파일들로 설정
            val multipartFiles: List<File>? = if (introFiles.size > 1) introFiles.subList(1, introFiles.size) else null

            // 필수 필드 유효성 검사
            val introduction = binding.etSentenceCreate.text.toString().trim()
            val title = binding.etTitleCreate.text.toString().trim()
            val description = binding.etDescriptionCreate.text.toString().trim()
            val introLyrics = binding.etIntroLyricsCreate.text.toString().trim()

            if (introduction.isEmpty() || title.isEmpty() || description.isEmpty()) {
                Toast.makeText(requireContext(), "모든 필수 정보를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 실제 데이터를 가져오는 로직 구현 (필요 시 수정)
            val actualBasePostId = ""
            val actualMemberId = ""
            val actualBasePost = ""
            val actualThumbnailIcon = ""
            val actualLyrics = ""
            val actualIntroType = ""
            val introMultipartFileInGame: List<File> = introFiles // 이미 변환한 introFiles 리스트 사용
            val introMediaUrlFromGame: String = introFiles.firstOrNull()?.absolutePath ?: ""

            // 서버로 데이터를 전송하는 로직
            viewModel.sendData(
                basePostId = actualBasePostId,
                memberId = actualMemberId,
                sourceType = "CREATION_GAME",
                basePost = actualBasePost,
                thumbnailIcon = actualThumbnailIcon,
                isLiked = false,
                isBookmarked = false,
                introduction = introduction,
                title = title,
                description = description,
                gameFiles = gameFiles,
                imageUrls = imageUrls,
                gameTags = selectedTags, // Chip에서 선택된 텍스트를 int로 변환
                introLyricsInGame = introLyrics,
                lyrics = actualLyrics,
                introType = actualIntroType,
                introTags = selectedTags, // 선택된 chip들의 텍스트를 서버로 전송
                introFiles = introFiles,
                introMultipartFileInGame = multipartFiles ?: emptyList(),
                introMediaUrlFromGame = introMediaUrlFromGame ?: ""
            )
        }
    }
    fun Uri.toFile(context: Context): File? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(this)
            val fileName = this.lastPathSegment ?: "temp_file"
            val file = File(context.cacheDir, fileName)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setupMediaLaunchers() {
        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                gameCreateAdapter.addMedia(photoUri, isVideo = false, isAudio = false)
                checkRecyclerViewVisibility()
            }
        }

        videoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (isIntroMode) {
                    introCreateAdapter.addMedia(videoUri, isVideo = true, isAudio = false)
                } else {
                    gameCreateAdapter.addMedia(videoUri, isVideo = true, isAudio = false)
                }
                checkRecyclerViewVisibility()
            }
        }

        audioLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val audioUri = result.data?.data ?: return@registerForActivityResult
                introCreateAdapter.addMedia(audioUri, isVideo = false, isAudio = true)
                checkRecyclerViewVisibility()
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                val tempFile = FileUtil.createTempFile(requireContext(), uri)
                val fileExtension = FileUtil.getFileExtension(requireContext(), uri)
                val isVideo = fileExtension in MediaExtensions.VIDEO_EXTENSIONS
                val isAudio = fileExtension in MediaExtensions.AUDIO_EXTENSIONS

                if (isIntroMode) {
                    if (isAudio && introCreateAdapter.isAudioExist()) {
                        Toast.makeText(requireContext(), "인트로는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        introCreateAdapter.addMedia(uri, isVideo, isAudio)
                        checkRecyclerViewVisibility()
                    }
                } else {
                    if (isVideo && gameCreateAdapter.isVideoExist()) {
                        Toast.makeText(requireContext(), "비디오는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        gameCreateAdapter.addMedia(uri, isVideo, isAudio)
                        checkRecyclerViewVisibility()
                    }
                }
            }
        }

        audiofileLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                val tempFile = FileUtil.createTempFile(requireContext(), uri)
                val fileExtension = FileUtil.getFileExtension(requireContext(), uri)
                val isAudio = fileExtension in MediaExtensions.AUDIO_EXTENSIONS
                if (isAudio && introCreateAdapter.isAudioExist()) {
                    Toast.makeText(requireContext(), "오디오는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                } else if (isAudio) {
                    introCreateAdapter.addMedia(uri, isVideo = false, isAudio = true)
                    checkRecyclerViewVisibility()
                }
            }
        }
    }

    private fun setupPermissionLauncher() {
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireActivity(), "권한이 요청되었습니다", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireActivity(), "권한 요청이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showIntroMediaSelectionDialog() {
        val options = arrayOf("녹음파일", "비디오 파일")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("선택하세요")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> if (introCreateAdapter.isTotalExists()) {
                        Toast.makeText(requireContext(), "인트로는 하나만 추가할 수 있습니다", Toast.LENGTH_SHORT).show()
                    } else {
                        openAudioFilePicker()
                    }
                    1 -> if (introCreateAdapter.isTotalExists()) {
                        Toast.makeText(requireContext(), "인트로는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        openVideoFilePicker()
                    }
                }
            }
        isIntroMode = true // Intro 모드로 설정
        builder.show()
    }

    private fun openAudioFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
        }
        audiofileLauncher.launch(intent)
    }

    private fun openVideoFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "video/*"
        }
        galleryLauncher.launch(intent)
    }

    private fun showTutorialMediaSelectionDialog() {
        val options = arrayOf("사진촬영", "비디오 촬영")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("선택하세요")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openCamera()
                    1 -> if (gameCreateAdapter.isVideoExist()) {
                        Toast.makeText(requireContext(), "비디오는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        recordVideo()  // 비디오 촬영
                    }
                }
            }
        isIntroMode = false // Tutorial 모드로 설정
        builder.show()
    }

    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = createImageFile()
        photoUri = FileProvider.getUriForFile(
            requireContext(),
            "${info.sul_game.BuildConfig.APPLICATION_ID}.fileprovider",
            file
        )
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        cameraLauncher.launch(intent)
    }

    fun recordVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val file = createVideoFile()
        videoUri = FileProvider.getUriForFile(requireContext(), "${info.sul_game.BuildConfig.APPLICATION_ID}.fileprovider", file)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri)
        videoLauncher.launch(intent)
    }

    fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/* video/*"
        }
        galleryLauncher.launch(intent)
    }

    private fun createImageFile(extension: String = ".jpg"): File {
        return FileUtil.createImageFile(requireContext(), extension)
    }

    private fun createVideoFile(extension: String = ".mp4"): File {
        return FileUtil.createVideoFile(requireContext(), extension)
    }

    // 오디오 녹음 기능
    private fun startRecording() {
        val audioFile = createAudioFile()
        audioFilePath = audioFile.absolutePath

        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.DEFAULT)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFilePath)

            try {
                prepare()
                start()
                Toast.makeText(requireContext(), "녹음 시작", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(requireContext(), CustomError.audioError(e), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun stopRecording() {
        // 녹음 중지
        mediaRecorder?.apply {
            stop()
            release()
        }
        mediaRecorder = null

        // 파일 URI 생성
        val file = File(audioFilePath)
        val audioUri = FileProvider.getUriForFile(
            requireContext(),
            "${info.sul_game.BuildConfig.APPLICATION_ID}.fileprovider",
            file
        )
        Log.d("AudioUri", "Audio URI: $audioUri")

        // 어댑터에 추가
        introCreateAdapter.addMedia(audioUri, isVideo = false, isAudio = true)

        // 리사이클러뷰 상태 확인
        checkRecyclerViewVisibility()

        Toast.makeText(requireContext(), "녹음 종료", Toast.LENGTH_SHORT).show()
    }

    private fun createAudioFile(extension: String = ".aac"): File {
        val file = FileUtil.createAudioFile(requireContext(), extension)
        Log.d("AudioFile", "Audio file created at: ${file.absolutePath}")
        return file
    }

    fun recordAudio() {
        if (mediaRecorder == null) {
            startRecording()
            recordButton.setImageResource(R.drawable.btn_record) // 녹음 시작
        } else {
            stopRecording()
            recordButton.setImageResource(R.drawable.btn_audio) // 녹음 중지
        }
    }

    fun checkRecyclerViewVisibility() {
        binding.rvMediaCreate.visibility = if (gameCreateAdapter.itemCount > 0) VISIBLE else GONE
        binding.rvIntroCreate.visibility = if (introCreateAdapter.itemCount > 0) VISIBLE else GONE
    }

    private fun checkSelectionOnChipChange(chipGroups: List<ChipGroup>) {
        // 4개 이상의 Chip이 선택된 경우 즉시 처리
        if (selectedChipsCreate.size > MAXIMUM_CHIP_COUNT) {
            Toast.makeText(requireContext(), "${MAXIMUM_CHIP_COUNT}개까지만 선택 가능합니다", Toast.LENGTH_SHORT).show()
            val lastSelectedChip = selectedChipsCreate.last()
            lastSelectedChip.isChecked = false
            selectedChipsCreate.remove(lastSelectedChip)
        }
    }

    private fun checkSelectionOnButtonClick(chipGroups: List<ChipGroup>) {
        selectedChipsCreate.clear()

        var validSelection = true

        // 각 ChipGroup에서 최소 1개의 선택이 있는지 확인
        for (group in chipGroups) {
            var groupSelected = false
            for (i in 0 until group.childCount) {
                val chip = group.getChildAt(i) as Chip
                if (chip.isChecked) {
                    groupSelected = true
                    selectedChipsCreate.add(chip)
                }
            }
            if (!groupSelected) {
                validSelection = false
            }
        }

        // 조건에 맞지 않으면 에러 메시지 표시
        if (!validSelection) {
            Toast.makeText(requireContext(), "정보를 전부 입력해야합니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
