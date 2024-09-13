package info.sul_game.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import info.sul_game.R
import info.sul_game.databinding.ActivityCreateDetailBinding


class CreationGameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCreateDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}