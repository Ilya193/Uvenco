package ru.ikom.uvenco

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.ikom.catalog.di.featureCatalogModule
import ru.ikom.details.di.featureDetailsModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(appModule, featureCatalogModule, featureDetailsModule)
        }
    }
}