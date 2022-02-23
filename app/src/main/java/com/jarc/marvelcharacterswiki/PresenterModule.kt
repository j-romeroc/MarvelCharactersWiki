package com.jarc.marvelcharacterswiki

import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.usecases.ImagesUseCase
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenter
import com.jarc.marvelcharacterswiki.ui.presenters.CharacterPresenterImpl
import org.koin.dsl.module

val charactersPresenterModule = module {

    fun provideCharactersPresenterModule(charactersUseCase: CharactersUseCase,
                                         characterDetailUseCase: CharacterDetailUseCase,
                                         imagesUseCase: ImagesUseCase
    ) : CharacterPresenter =
        CharacterPresenterImpl(
            charactersUseCase,
            characterDetailUseCase,
            imagesUseCase)

    single{ provideCharactersPresenterModule(get(),get(),get())}
}