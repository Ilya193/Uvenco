package ru.ikom.details.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.details.data.SettingsDrinkRepositoryImpl
import ru.ikom.details.domain.SettingsDrinkRepository
import ru.ikom.details.presentation.DetailsViewModel

val featureDetailsModule = module {
    viewModel<DetailsViewModel> { params ->
        DetailsViewModel(drinkId = params.get(), get())
    }

    factory<SettingsDrinkRepository> {
        SettingsDrinkRepositoryImpl(get())
    }
}