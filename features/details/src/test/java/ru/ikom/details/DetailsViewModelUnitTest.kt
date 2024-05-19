package ru.ikom.details

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import ru.ikom.details.domain.DrinkDomain
import ru.ikom.details.domain.SettingsDrinkRepository
import ru.ikom.details.presentation.DetailsViewModel
import ru.ikom.details.presentation.Event
import ru.ikom.details.presentation.toDrinkUi

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelUnitTest {

    private lateinit var testDispatcher: TestDispatcher

    @Before
    fun before() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `check starting state DetailsViewModel`() = runTest {
        val repository = Mockito.mock(SettingsDrinkRepository::class.java)
        val drink = DrinkDomain(0, "Name", 0.33f, null)
        Mockito.`when`(repository.fetchDrink(0)).thenReturn(drink)
        val viewModel = DetailsViewModel(0, repository, testDispatcher)
        advanceUntilIdle()
        assertEquals(viewModel.uiState.value.drink, drink.toDrinkUi())
    }

    @Test
    fun `check change name with new value and value less MAX_LENGTH`() = runTest {
        val repository = Mockito.mock(SettingsDrinkRepository::class.java)
        val drink = DrinkDomain(0, "Name", 0.33f, null)
        Mockito.`when`(repository.fetchDrink(0)).thenReturn(drink)
        val viewModel = DetailsViewModel(0, repository, testDispatcher)
        viewModel.action(Event.ChangeName("value < MAX_LENGTH"))
        advanceUntilIdle()
        assertEquals(viewModel.uiState.value.isChanges, true)
    }

    @Test
    fun `check change name with old value`() = runTest {
        val repository = Mockito.mock(SettingsDrinkRepository::class.java)
        val drink = DrinkDomain(0, "Name", 0.33f, null)
        Mockito.`when`(repository.fetchDrink(0)).thenReturn(drink)
        val viewModel = DetailsViewModel(0, repository, testDispatcher)
        viewModel.action(Event.ChangeName("Name"))
        advanceUntilIdle()
        assertEquals(viewModel.uiState.value.isChanges, false)
    }

    @Test
    fun `check change price with new value and value less MAX_PRICE`() = runTest {
        val repository = Mockito.mock(SettingsDrinkRepository::class.java)
        val drink = DrinkDomain(0, "Name", 0.33f, null)
        Mockito.`when`(repository.fetchDrink(0)).thenReturn(drink)
        val viewModel = DetailsViewModel(0, repository, testDispatcher)
        viewModel.action(Event.ChangePrice(100))
        advanceUntilIdle()
        assertEquals(viewModel.uiState.value.isChanges, true)
    }

    @Test
    fun `check change price with old value`() = runTest {
        val repository = Mockito.mock(SettingsDrinkRepository::class.java)
        val drink = DrinkDomain(0, "Name", 0.33f, 100)
        Mockito.`when`(repository.fetchDrink(0)).thenReturn(drink)
        val viewModel = DetailsViewModel(0, repository, testDispatcher)
        viewModel.action(Event.ChangePrice(100))
        advanceUntilIdle()
        assertEquals(viewModel.uiState.value.isChanges, false)
    }

    @Test
    fun `check switch for free price`() = runTest {
        val repository = Mockito.mock(SettingsDrinkRepository::class.java)
        val drink = DrinkDomain(0, "Name", 0.33f, 100)
        Mockito.`when`(repository.fetchDrink(0)).thenReturn(drink)
        val viewModel = DetailsViewModel(0, repository, testDispatcher)
        viewModel.action(Event.SellForFree(true))
        advanceUntilIdle()
        assertEquals(viewModel.uiState.value.switchIsFree, true)
        assertEquals(viewModel.uiState.value.inputPrice, "")
    }
}