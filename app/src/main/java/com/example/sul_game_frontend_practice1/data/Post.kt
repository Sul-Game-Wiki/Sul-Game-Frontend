package com.example.sul_game_frontend_practice1.data

data class Post(
    var type : String,
    var title : String,
    var content : String,
    var writer : User,
    var comment : MutableList<Comment>,
    var heartCnt : Int
)
