package info.sul_game.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import info.sul_game.R
import info.sul_game.databinding.ActivityIntroduceGradeBinding

class IntroduceGradeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityIntroduceGradeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroduceGradeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCloseIntroduce.setOnClickListener {
            finish()
        }
    }
}