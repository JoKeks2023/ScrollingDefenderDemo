package com.example.marsphotos.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.network.Esp32Api
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class AppState { IDLE, ARMED, CONFIRMED, PLAYING_VIDEO }

class GalleryViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppState.IDLE)
    val state: StateFlow<AppState> = _state

    private var confirmJob: Job? = null
    private var triggerJob: Job? = null
    private var pollJob: Job? = null

    fun onTouch() {
        when (_state.value) {
            AppState.IDLE -> arm()
            AppState.ARMED -> confirm()
            else -> Unit
        }
    }

    private fun arm() {
        _state.value = AppState.ARMED
        confirmJob = viewModelScope.launch {
            delay(15_000)
            triggerJob?.cancel()
            _state.value = AppState.IDLE
        }
        triggerJob = viewModelScope.launch {
            delay(20_000)
            if (_state.value == AppState.CONFIRMED) {
                startVideo()
            }
        }
    }

    private fun confirm() {
        confirmJob?.cancel()
        _state.value = AppState.CONFIRMED
    }

    private fun startVideo() {
        _state.value = AppState.PLAYING_VIDEO
        pollJob = viewModelScope.launch {
            while (isActive) {
                val status = runCatching { Esp32Api.retrofitService.getStatus() }.getOrNull()
                if (status?.defaultResponse == true) {
                    _state.value = AppState.IDLE
                    break
                }
                delay(500)
            }
        }
    }

    override fun onCleared() {
        confirmJob?.cancel()
        triggerJob?.cancel()
        pollJob?.cancel()
    }
}
