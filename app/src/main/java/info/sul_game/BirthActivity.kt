package info.sul_game

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import info.sul_game.databinding.ActivityBirthBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BirthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBirthBinding
    private val calendar: Calendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBirthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCloseBirth.setOnClickListener{
            startActivity(Intent(this, StartActivity::class.java))
            finish()
        }

        binding.etDateBirth.isFocusable = false
        binding.etDateBirth.isFocusableInTouchMode = false
        binding.etDateBirth.isClickable = true

        binding.etDateBirth.setOnClickListener{
            showDatePickerDialog()
        }

        binding.btnNextBirth.setOnClickListener{
            startActivity(Intent(this, SchoolActivity::class.java))
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d/%02d/%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.etDateBirth.setText(formattedDate)

                buttonClickable()
            },
            year, month, day
        )
        datePickerDialog.show()
    }

    private fun availableBirth(birth: String): Boolean {
        val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val birthDate = sdf.parse(birth) ?: return false // Null 체크 추가
        val today = Calendar.getInstance()

        val birthCalendar = Calendar.getInstance()
        birthCalendar.time = birthDate

        var age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age >= 14
    }


    /**
     * Button Clickable 설정
     * 입력한 닉네임이 제대로 쓰여졌으면 클릭 허용
     */
    private fun buttonClickable() {
        if (availableBirth(binding.etDateBirth.text.toString())) {
            binding.btnNextBirth.isEnabled = true
            binding.btnNextBirth.backgroundTintList = ContextCompat.getColorStateList(this,
                info.sul_game.R.color.main_color
            )
        } else {
            binding.btnNextBirth.isEnabled = false
            binding.btnNextBirth.backgroundTintList = ContextCompat.getColorStateList(this,
                info.sul_game.R.color.light_gray
            )

            showUnderageDialog()
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

        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this,
            info.sul_game.R.color.main_color
        ))
    }
}