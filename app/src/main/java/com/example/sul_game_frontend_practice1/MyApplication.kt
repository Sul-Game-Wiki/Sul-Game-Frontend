package com.example.sul_game_frontend_practice1

import android.app.Application
import com.example.sul_game_frontend_practice1.retrofit.Member
import com.example.sul_game_frontend_practice1.retrofit.MemberContentInteraction
import com.example.sul_game_frontend_practice1.retrofit.NetworkManager
import com.example.sul_game_frontend_practice1.retrofit.RetrofitClient

class MyApplication : Application() {
    var member : Member? = null
    var memberContentInteraction : MemberContentInteraction? = null

    override fun onCreate(){
        super.onCreate()

    }
}