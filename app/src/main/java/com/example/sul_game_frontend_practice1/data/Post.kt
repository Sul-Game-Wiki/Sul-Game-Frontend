package com.example.sul_game_frontend_practice1.data

import java.time.LocalDateTime

data class Post(
    var type : String,
    var title : String,
    var content : String,
    var writer : User,
    var createdAt : LocalDateTime,
    var comment : MutableList<Comment>,
    var heartCnt : Int
)
