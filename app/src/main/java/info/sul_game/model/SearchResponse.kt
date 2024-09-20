package info.sul_game.model

data class SearchResponse(
    val intros : List<Intro>,
    val officialGames : List<OfficialGame>,
    val creationGames : List<CreationGame>
)
