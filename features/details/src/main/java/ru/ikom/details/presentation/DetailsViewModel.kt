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
            val drink = repository.fetchDrink(drinkId).toDrinkUi()
            startUiState = DetailsUiState(
                drink,
                inputName = drink.name,
                inputPrice = drink.price?.toString() ?: "",
                switchIsFree = drink.price == null
            )
            _uiState.value = startUiState.copy()
        }
    }

    fun action(event: Event) = viewModelScope.launch(dispatcher) {
        when (event) {
            is Event.ChangeName -> changeName(event.name)
            is Event.ChangePrice -> changePrice(event.price)
            is Event.Save -> save(event.name, event.price)
            is Event.SellForFree -> sellForFree(event.isFree)
            is Event.InputName -> inputName(event.name)
            is Event.InputPrice -> inputPrice(event.price)
            is Event.Reset -> reset()
        }
    }

    private fun changeName(name: String) = viewModelScope.launch(dispatcher) {
        _uiState.update { it.copy(isChanges = name != startUiState.drink?.name && name.isNotEmpty() && name.length <= MAX_LENGTH) }
    }

    private fun changePrice(price: Int?) = viewModelScope.launch(dispatcher) {
        if (price == null) _uiState.update { it.copy(isChanges = price != startUiState.drink?.price) }
        else _uiState.update { it.copy(isChanges = price != startUiState.drink?.price && price >= MIN_PRICE && price <= MAX_PRICE) }
    }

    private fun save(name: String, price: Int?) = viewModelScope.launch(dispatcher) {
        startUiState.drink?.let { drink ->
            repository.updateDrink(drink.copy(name = name, price = price).toDrinkDomain())
            _uiState.update { it.copy(isCompleted = true) }
        }
    }

    private fun sellForFree(isFree: Boolean) = viewModelScope.launch(dispatcher) {
        if (isFree) changePrice(null)
        else changePrice(startUiState.drink?.price)
        _uiState.update { it.copy(inputPrice = if (isFree) "" else startUiState.inputPrice, switchIsFree = isFree) }
    }

    private fun inputName(name: String) = viewModelScope.launch(dispatcher) {
        _uiState.update { it.copy(inputName = name) }
    }

    private fun inputPrice(price: String) = viewModelScope.launch(dispatcher) {
        _uiState.update { it.copy(inputPrice = price, switchIsFree = price.isEmpty()) }
    }

    fun reset() {
        _uiState.update { it.copy(isCompleted = false) }
    }
}

data class DetailsUiState(
    val drink: DrinkUi? = null,
    val isChanges: Boolean = false,
    val isCompleted: Boolean = false,
    val inputName: String = "",
    val inputPrice: String = "",
    val switchIsFree: Boolean = false
)

sealed interface Event {
    @JvmInline
    value class ChangeName(val name: String) : Event

    @JvmInline
    value class ChangePrice(val price: Int?) : Event

    @JvmInline
    value class SellForFree(val isFree: Boolean) : Event

    @JvmInline
    value class InputName(val name: String) : Event

    @JvmInline
    value class InputPrice(val price: String) : Event

    class Save(val name: String, val price: Int?) : Event

    class Reset : Event
}