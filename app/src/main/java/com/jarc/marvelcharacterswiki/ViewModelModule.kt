package com.jarc.marvelcharacterswiki

import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.usecases.ImagesUseCase
import com.jarc.marvelcharacterswiki.ui.viewmodels.CharacterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterViewModelModule = module {
    fun provideCharactersViewModelModule(
        charactersUseCase: CharactersUseCase,
        characterDetailUseCase: CharacterDetailUseCase,
        imagesUseCase: ImagesUseCase
    ): CharacterViewModel =
        CharacterViewModel(
            charactersUseCase,
            characterDetailUseCase,
            imagesUseCase
        )

    viewModel { provideCharactersViewModelModule(get(), get(), get()) }
}