package com.example.sul_game_frontend_practice1.data

import android.provider.ContactsContract.CommonDataKinds.Organization

data class User(
    var name : String,
    var organization : String?,
    var profileImage : Int
)
