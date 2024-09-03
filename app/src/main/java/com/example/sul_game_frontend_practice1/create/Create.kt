package com.example.sul_game_frontend_practice1.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.databinding.ActivityCreateBinding
import com.example.sul_game_frontend_practice1.databinding.ActivityMainBinding

class Create : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private var isIncludeCreateCreateVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangeCreate.setOnClickListener {
            if(isIncludeCreateCreateVisible){
                binding.includeOfficialCreate.root.visibility= View.GONE
                binding.includeCreateCreate.root.visibility = View.VISIBLE
                binding.btnChangeCreate.text ="창작"

            }else{
                binding.includeOfficialCreate.root.visibility= View.VISIBLE
                binding.includeCreateCreate.root.visibility = View.GONE
                binding.btnChangeCreate.text ="공식"

            }
            isIncludeCreateCreateVisible=!isIncludeCreateCreateVisible

        }
    }


}