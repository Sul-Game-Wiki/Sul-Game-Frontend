package info.sul_game.utils

class CustomError {
    companion object {
        fun audioError(e: Exception): String {
            return "녹음 시작 실패: ${e.message}"
        }
    }
}