package com.example.sul_game_frontend_practice1.editprofile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sul_game_frontend_practice1.R
import com.example.sul_game_frontend_practice1.data.User
import com.example.sul_game_frontend_practice1.databinding.ActivityEditProfileBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dummyUser = User("구해조", "세종대학교", R.drawable.img_student,"user@gmail.com", LocalDate.of(2000, 1, 1))

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        binding.et1Editprofile.setText(dummyUser.name)
        binding.et2Editprofile.setText(dummyUser.organization)
        binding.et3Editprofile.setText(dummyUser.email)
        binding.et4Editprofile.setText(dummyUser.birthDate.format(dateFormatter))
    }
}