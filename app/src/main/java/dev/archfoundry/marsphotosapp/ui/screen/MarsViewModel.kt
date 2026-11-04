package dev.archfoundry.marsphotosapp.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import dev.archfoundry.marsphotosapp.MarsPhotosApplication
import dev.archfoundry.marsphotosapp.data.MarsPhotosRepository
import dev.archfoundry.marsphotosapp.network.MarsPhoto
import kotlinx.coroutines.launch
import java.io.IOException

class MarsViewModel(private val marsPhotosRepository: MarsPhotosRepository) : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(value = MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    fun getMarsPhotos() {
        viewModelScope.launch {
            marsUiState = try {
                val result: List<MarsPhoto> = marsPhotosRepository.getMarsPhotos()
                val myPhoto: MarsPhoto = result[0]
                MarsUiState.Success(photosList = result)
            } catch (_: IOException) {
                MarsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer<MarsViewModel> {
                val application = (this[APPLICATION_KEY] as MarsPhotosApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotosRepository)
            }
        }
    }
}

sealed interface MarsUiState {
    data class Success(val photosList: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}
