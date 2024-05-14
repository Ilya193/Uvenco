package ru.ikom.uvenco

import androidx.room.Room
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.ikom.database.drinks.DrinksDB
import ru.ikom.database.drinks.DrinksDao

val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel()
    }

    single<DrinksDao> {
        Room.databaseBuilder(get(), DrinksDB::class.java, "drinks.db")
            .createFromAsset("drinks.db")
            .build().drinksDao()
    }
}