package info.sul_game.model

import info.sul_game.viewmodel.CreationCreateViewModel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import info.sul_game.model.CreationCreateRepository

class CreationCreateViewModelFactory(
    private val repository: CreationCreateRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreationCreateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CreationCreateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
