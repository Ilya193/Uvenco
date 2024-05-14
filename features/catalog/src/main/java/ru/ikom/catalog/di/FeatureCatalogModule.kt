package ru.ikom.catalog.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.catalog.data.DrinksRepositoryImpl
import ru.ikom.catalog.domain.DrinksRepository
import ru.ikom.catalog.presentation.CatalogViewModel
import ru.ikom.drinks.DrinksCacheDataSource
import ru.ikom.drinks.DrinksCacheDataSourceImpl

val featureCatalogModule = module {
    viewModel<CatalogViewModel> {
        CatalogViewModel(get())
    }

    factory<DrinksCacheDataSource> {
        DrinksCacheDataSourceImpl(get())
    }

    factory<DrinksRepository> {
        DrinksRepositoryImpl(get())
    }
}