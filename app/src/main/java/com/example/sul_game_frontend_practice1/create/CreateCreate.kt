package com.example.sul_game_frontend_practice1.create

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ActivityCreateCreateBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.json.JSONObject

class CreateCreate : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCreateBinding
    private val selectedChipsCreate = mutableListOf<Chip>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val jsonObject= JSONObject()
        jsonObject.put("email", email.toString())
        jsonObject.put("password", binding.etNewPasswordOkInput.text.toString())


    }
}
