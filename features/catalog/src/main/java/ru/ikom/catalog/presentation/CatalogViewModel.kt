package ru.ikom.catalog.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ikom.catalog.domain.DrinksRepository

class CatalogViewModel(
    private val repository: DrinksRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            repository.fetchDrinks().collect {
                _uiState.value = CatalogUiState(it.map { it.toDrinkUi() })
            }
        }
    }
}

data class CatalogUiState(
    val drinks: List<DrinkUi> = emptyList()
)