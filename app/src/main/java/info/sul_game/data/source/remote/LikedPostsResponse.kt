package info.sul_game.data.source.remote

data class LikedPostsResponse(
    val likedOfficalGames : List<BasePost>,
    val likedCreationGames : List<BasePost>,
    val likedIntros : List<BasePost>
)