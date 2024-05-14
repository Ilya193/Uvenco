package ru.ikom.uvenco

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private var timerTemperature = Timer()
    private val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = MainUiState(sdf.format(Date()), Random.nextInt(85, 95).toString())
    }

    fun start() {
        timerTemperature = Timer()
        timerTemperature.scheduleAtFixedRate(0, 1000) {
            _uiState.value = MainUiState(sdf.format(Date()), Random.nextInt(85, 95).toString())
        }
    }

    fun stop() {
        timerTemperature.cancel()
    }
}

data class MainUiState(
    val date: String = "",
    val temperature: String = ""
)