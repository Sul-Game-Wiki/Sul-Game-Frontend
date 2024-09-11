package info.sul_game.data.source.remote

data class BookmarkedPostsResponse(
    val bookmarkedOfficialGameIds : List<BasePost>,
    val bookmarkedCreationGameIds : List<BasePost>,
    val bookmarkedIntroIds : List<BasePost>
)