package com.example.sul_game_frontend_practice1.create

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sul_game_frontend_practice1.BuildConfig
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ActivityCreateCreateBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.json.JSONObject
import java.io.File

class CreateCreate : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCreateBinding
    private val selectedChipsCreate = mutableListOf<Chip>()
    private lateinit var CreateCreate_Adapter: CreateCreate_Adapter
    private lateinit var mediaUri: Uri
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var videoLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CreateCreate_Adapter = CreateCreate_Adapter(mutableListOf())
        binding.rvMediaCreate.layoutManager = LinearLayoutManager(this)
        binding.rvMediaCreate.adapter = CreateCreate_Adapter

        cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {result -> if(result.resultCode == RESULT_OK){
            CreateCreate_Adapter.addMedia(mediaUri)
            checkRecyclerViewVisibility()
        } }

        binding.btnCameraCreate.setOnClickListener{
            showMediaSelectionDialog()
        }
        checkRecyclerViewVisibility()




        // ChipGroups와 Button을 가져옵니다.
        val chipGroups = listOf(
            binding.chipGroupLevelCreate,
            binding.chipGroupCountCreate,
            binding.chipGroupNoiseCreate,
            binding.chipGroupEtcCreate
        )

        // ChipGroup의 선택 이벤트 리스너 설정
        for (group in chipGroups) {
            group.setOnCheckedChangeListener { _, _ -> checkSelectionOnChipChange(chipGroups) }
        }

        // Button 클릭 이벤트 설정
        binding.btnEnrollCreate.setOnClickListener {
            checkSelectionOnButtonClick(chipGroups)
        }
    }

    private fun showMediaSelectionDialog(){
        val options = arrayOf("사진촬영", "비디오 촬영")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("선택하세요")
            .setItems(options){_, which ->
                when(which){
                    0 -> openCamera()
                    1 ->  if (CreateCreate_Adapter.doesVideoExist()) {
                        Toast.makeText(this, "비디오는 하나만 추가할 수 있습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        recordVideo()  // 비디오 촬영
                    }
                }
            }
        builder.show()
    }
    private fun openCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = createImageFile()
        mediaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mediaUri)
        cameraLauncher.launch(intent)
    }

    private fun recordVideo(){
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        val file = createVideoFile()
        mediaUri = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", file)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,mediaUri)
        videoLauncher.launch(intent)
    }
    private fun createImageFile(extension: String =".jpg"): File {
        val storageDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val validExtension = when(extension.lowercase()){
            ".jpg",".jpeg",".png" -> extension
            else -> ".jpg"
        }
        return File.createTempFile(
            "IMG_${System.currentTimeMillis()}_", /* 파일 이름 */
            validExtension,
            storageDir
        )
    }
    private fun createVideoFile(extension: String = ".mp4"): File {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES)

        // 비디오 확장자 유효성 검사
        val validExtension = when (extension.lowercase()) {
            ".mp4", ".avi", ".mov" -> extension
            else -> ".mp4" // 기본 확장자
        }

        return File.createTempFile(
            "VIDEO_${System.currentTimeMillis()}_", /* 파일 이름 */
            validExtension, /* 파일 확장자 */
            storageDir /* 파일 저장 위치 */
        )
    }

    private fun checkRecyclerViewVisibility(){
        if(CreateCreate_Adapter.itemCount>0){
            binding.rvMediaCreate.visibility =VISIBLE
        }else{
            binding.rvMediaCreate.visibility = GONE
        }
    }


    private fun checkSelectionOnChipChange(chipGroups: List<ChipGroup>) {
        // 4개 이상의 Chip이 선택된 경우 즉시 처리
        if (selectedChipsCreate.size > 4) {
            Toast.makeText(this, "4개까지만 선택 가능합니다", Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this, "정보를 전부 입력해야합니다", Toast.LENGTH_SHORT).show()
        }
//        val jsonObject= JSONObject()
//        jsonObject.put("email", email.toString())
//        jsonObject.put("password", binding.etNewPasswordOkInput.text.toString())


    }
}
