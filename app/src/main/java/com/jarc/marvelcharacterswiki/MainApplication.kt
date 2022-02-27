package com.jarc.marvelcharacterswiki

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.jarc.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            androidLogger(Level.ERROR)
            modules(
                listOf(
                    charactersRepoModule,
                    characterDetailRepoModule,
                    imagesRepoModule,
                    charactersUseCaseModule,
                    characterDetailsUseCaseModule,
                    imagesUseCaseModule,
                    characterServiceModule,
                    imageServiceModule,
                    characterViewModelModule
                )
            )
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}