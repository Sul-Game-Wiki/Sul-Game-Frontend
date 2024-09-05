package com.example.sul_game_frontend_practice1.retrofit

data class LikedPostsResponse(
    val likedOfficalGames : List<BasePost>,
    val likedCreationGames : List<BasePost>,
    val likedIntros : List<BasePost>
)

