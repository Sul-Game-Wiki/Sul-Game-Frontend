package com.example.sul_game_frontend_practice1.data

import java.time.LocalDateTime

data class Comment(
    var writer : User,
    var content : String,
    var date : LocalDateTime
)
