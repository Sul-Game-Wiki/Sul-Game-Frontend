package com.example.sul_game_frontend_practice1

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.sul_game_frontend_practice1.databinding.ActivitySignUpBinding
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val calendar: Calendar = Calendar.getInstance()
    private lateinit var schoolNames: List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadSchoolNames()

        binding.btnCloseSignup.setOnClickListener {
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        binding.etNameSignup.addTextChangedListener {
            val maxLength = "/10"
            binding.tvCountLengthSignup.text =
                binding.etNameSignup.text.length.toString() + maxLength

            val text = binding.etNameSignup.text.toString()

            // 사용 가능 닉네임인지 확인
            if (text.isEmpty()) {
                binding.tvHint1Signup.visibility = View.VISIBLE
                binding.tvHint2Signup.visibility = View.INVISIBLE
                binding.tvHint3Signup.visibility = View.INVISIBLE
            } else if (availableName(text)) {
                binding.tvHint1Signup.visibility = View.INVISIBLE
                binding.tvHint2Signup.visibility = View.VISIBLE
                binding.tvHint3Signup.visibility = View.INVISIBLE
            } else {
                binding.tvHint1Signup.visibility = View.INVISIBLE
                binding.tvHint2Signup.visibility = View.INVISIBLE
                binding.tvHint3Signup.visibility = View.VISIBLE
            }

            buttonClickable()
        }

        binding.etDateSignup.isFocusable = false
        binding.etDateSignup.isFocusableInTouchMode = false
        binding.etDateSignup.isClickable = true

        binding.etDateSignup.setOnClickListener{
            showDatePickerDialog()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, schoolNames)
        binding.tvSchoolSignup.setAdapter(adapter)

        binding.tvSchoolSignup.addTextChangedListener {
            buttonClickable()
        }

        binding.btnNextSignup.setOnClickListener{
            startActivity(Intent(this, PermissionActivity::class.java))
            finish()
        }
    }

    /**
     * TODO : 서버랑 합체해야됨
     * 사용 가능 닉네임인지 확인
     */
    private fun availableName(name: String): Boolean {
        return true
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d/%02d/%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.etDateSignup.setText(formattedDate)

                buttonClickable()
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun availableBirth(birth: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.US)
            val birthDate = sdf.parse(birth) ?: return false
            val today = Calendar.getInstance()
            val birthCalendar = Calendar.getInstance().apply { time = birthDate }
            var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

            if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
                age--
            }
            age >= 14
        } catch (e: Exception) {
            false
        }
    }

    private fun showUnderageDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("만 14세 이상만 가입 가능합니다.")
            .setCancelable(false)
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
        val alert = builder.create()

        val drawable = GradientDrawable()
        drawable.cornerRadius = 40f
        drawable.setColor(ContextCompat.getColor(this, android.R.color.white))

        alert.window?.setBackgroundDrawable(drawable)

        alert.show()

        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this, R.color.main_color))
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
        val dateText = binding.etDateSignup.text.toString()
        val isNameAvailable = binding.tvHint2Signup.visibility == View.VISIBLE
        val isBirthValid = availableBirth(dateText)
        val isSchoolValid = binding.tvSchoolSignup.text.toString() in schoolNames

        if (isNameAvailable && isBirthValid && isSchoolValid) {
            binding.btnNextSignup.isEnabled = true
            binding.btnNextSignup.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.main_color)
        } else {
            if (!isBirthValid && dateText.isNotEmpty()) {
                showUnderageDialog()
            }
            binding.btnNextSignup.isEnabled = false
            binding.btnNextSignup.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.light_gray)
        }
    }
}