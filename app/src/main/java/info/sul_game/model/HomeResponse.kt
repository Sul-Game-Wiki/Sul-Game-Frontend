package info.sul_game.model

class HomeResponse {
    val latestCreationGames: List<LatestCreationGame>? = null
    val latestIntros: List<LatestIntro>? = null
    val officialGamesByLikes: List<OfficialGame>? = null
    val creationGamesByDailyScore: List<CreationGame>? = null
    val introsByDailyScore: List<Intro>? = null
    val officialGamesByDailyScore: List<OfficialGame>? = null
    val introsByLikes: List<Intro>? = null
    val introsByViews: List<Intro>? = null
    val gamesByWeeklyScore: List<Game>? = null
}