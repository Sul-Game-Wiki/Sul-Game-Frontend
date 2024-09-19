// CreationCreateViewModel.kt
package info.sul_game.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import info.sul_game.model.CreationCreateRepository
import info.sul_game.model.CreationGame
import info.sul_game.utils.GameTags
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File

class CreationCreateViewModel(private val repository: CreationCreateRepository) : ViewModel() {

    private val _responseMessage = MutableLiveData<String>()
    val responseMessage: LiveData<String> get() = _responseMessage

    // selectedTags 프로퍼티 추가
    private val _selectedTags = MutableLiveData<List<GameTags>>(emptyList())
    val selectedTags: LiveData<List<GameTags>> get() = _selectedTags

    fun updateSelectedTags(tags: List<GameTags>) {
        _selectedTags.value = tags
    }

    fun sendData(
        basePostId: String,
        memberId: String,
        sourceType: String,
        basePost: String,
        thumbnailIcon: String,
        isLiked: Boolean,
        isBookmarked: Boolean,
        introduction: String,
        title: String,
        description: String,
        gameFiles: List<File>,
        imageUrls: List<String>,
        gameTags: List<Int>,
        introLyricsInGame: String,
        introFiles: List<File>,
        introMultipartFileInGame: List<File>,
        lyrics: String,
        introType: String,
        introTags: List<String>,
        introMediaUrlFromGame: String
    ) {
        viewModelScope.launch {
            try {
                val response = repository.sendCreationData(
                    basePostId,
                    memberId,
                    sourceType,
                    basePost,
                    thumbnailIcon,
                    isLiked,
                    isBookmarked,
                    introduction,
                    title,
                    description,
                    gameFiles,
                    imageUrls,
                    gameTags,
                    introLyricsInGame,
                    introFiles,
                    introMultipartFileInGame,
                    lyrics,
                    introType,
                    introTags,
                    introMediaUrlFromGame
                    // relatedOfficialGameId 제거
                )

                if (response.isSuccessful) {
                    _responseMessage.postValue("성공적으로 등록되었습니다.")
                } else {
                    _responseMessage.postValue("등록에 실패했습니다: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _responseMessage.postValue("오류가 발생했습니다: ${e.message}")
                Log.e("CreationCreateViewModel", "Error sending data", e)
            }
        }
    }
}
