package info.sul_game.data.source.remote

import android.app.Application

class MyApplication : Application() {
    var member : Member? = null
    var memberContentInteraction : MemberContentInteraction? = null

    override fun onCreate(){
        super.onCreate()

    }
}