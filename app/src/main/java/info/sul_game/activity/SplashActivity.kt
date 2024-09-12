package info.sul_game.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.sul_game.databinding.ActivityStartBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLoginStart.setOnClickListener{
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}