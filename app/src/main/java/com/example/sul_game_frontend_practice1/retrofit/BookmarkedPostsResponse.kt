package com.example.sul_game_frontend_practice1.retrofit

data class BookmarkedPostsResponse(
    val bookmarkedOfficialGameIds : List<BasePost>,
    val bookmarkedCreationGameIds : List<BasePost>,
    val bookmarkedIntroIds : List<BasePost>
)
