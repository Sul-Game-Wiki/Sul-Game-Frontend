package com.example.sul_game_frontend_practice1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.sul_game_frontend_practice1.databinding.ActivitySchoolBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.File

class SchoolActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySchoolBinding
    private lateinit var schoolNames: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySchoolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCloseSchool.setOnClickListener{
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        loadSchoolNames()

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, schoolNames)
        binding.tvSchoolSchool.setAdapter(adapter)

        binding.tvSchoolSchool.addTextChangedListener {
            buttonClickable()
        }

//        binding.tvSchoolSchool.setOnItemClickListener { _, _, _, _ ->
//
//        }

        binding.btnNextSchool.setOnClickListener{
            startActivity(Intent(this, PermissionActivity::class.java))
            finish()
        }
    }

    private fun loadSchoolNames() {
        val inputStream = resources.openRawResource(R.raw.school)
        val jsonFile = inputStream.bufferedReader().use { it.readText() }

        val jsonObject = JSONObject(jsonFile)
        val recordsArray: JSONArray = jsonObject.getJSONArray("records")

        schoolNames = mutableListOf<String>().apply {
            for (i in 0 until recordsArray.length()) {
                val record: JSONObject = recordsArray.getJSONObject(i)
                add(record.getString("학교명"))
            }
        }
    }

    /**
     * Button Clickable 설정
     * 입력한 닉네임이 제대로 쓰여졌으면 클릭 허용
     */
    private fun buttonClickable() {
        if (binding.tvSchoolSchool.text.toString() in schoolNames) {
            binding.btnNextSchool.isEnabled = true
            binding.btnNextSchool.backgroundTintList = ContextCompat.getColorStateList(this, R.color.main_color)
        } else {
            binding.btnNextSchool.isEnabled = false
            binding.btnNextSchool.backgroundTintList = ContextCompat.getColorStateList(this, R.color.light_gray)
        }
    }
}