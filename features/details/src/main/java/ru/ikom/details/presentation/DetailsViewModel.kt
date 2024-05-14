package ru.ikom.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.ikom.details.domain.SettingsDrinkRepository

class DetailsViewModel(
    private val drinkId: Int,
    private val repository: SettingsDrinkRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()
    private var startUiState = DetailsUiState()

    init {
        viewModelScope.launch(dispatcher) {
            startUiState = DetailsUiState(repository.fetchDrink(drinkId).toDrinkUi())
            _uiState.value = startUiState.copy()
        }
    }

    fun action(event: Event) = viewModelScope.launch(dispatcher) {
        when (event) {
            is Event.ChangeName -> changeName(event.name)
            is Event.ChangePrice -> changePrice(event.price)
            is Event.Save -> save(event.name, event.price)
        }
    }

    private fun changeName(name: String) = viewModelScope.launch(dispatcher) {
        _uiState.update { it.copy(isChanges = name != startUiState.drink?.name && name.isNotEmpty()) }
    }

    private fun changePrice(price: Int) = viewModelScope.launch(dispatcher) {
        _uiState.update { it.copy(isChanges = price != startUiState.drink?.price && price != 0) }
    }

    private fun save(name: String, price: Int) = viewModelScope.launch(dispatcher) {
        startUiState.drink?.let { drink ->
            repository.updateDrink(drink.copy(name = name, price = price).toDrinkDomain())
            _uiState.update { it.copy(isCompleted = Unit) }
        }
    }

}

data class DetailsUiState(
    val drink: DrinkUi? = null,
    val isChanges: Boolean = false,
    val isCompleted: Unit? = null
)

sealed interface Event {
    data class ChangeName(val name: String) : Event
    data class ChangePrice(val price: Int) : Event
    data class Save(val name: String, val price: Int) : Event
}