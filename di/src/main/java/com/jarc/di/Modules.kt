package com.jarc.di

import com.jarc.data.repositories.CharacterDetailRepoImpl
import com.jarc.data.repositories.CharactersListRepoImpl
import com.jarc.data.repositories.ImageRepoImpl
import com.jarc.data.services.CharacterService
import com.jarc.data.services.ImageService
import com.jarc.domain.repositories.CharacterDetailRepo
import com.jarc.domain.repositories.CharactersListRepo
import com.jarc.domain.repositories.ImageRepo
import com.jarc.domain.usecases.CharacterDetailUseCase
import com.jarc.domain.usecases.CharactersUseCase
import com.jarc.domain.usecases.ImagesUseCase
import org.koin.dsl.module

val charactersRepoModule = module {

    fun provideCharactersRepoModule(service: CharacterService): CharactersListRepo =
        CharactersListRepoImpl(service)
    single { provideCharactersRepoModule(get()) }
}

val imagesRepoModule = module {

    fun provideImagesRepoModule(service: ImageService): ImageRepo = ImageRepoImpl(service)
    single { provideImagesRepoModule(get()) }
}

val characterDetailRepoModule = module {

    fun provideCharacterDetailRepoModule(service: CharacterService): CharacterDetailRepo =
        CharacterDetailRepoImpl(service)
    single { provideCharacterDetailRepoModule(get()) }
}

val charactersUseCaseModule = module {

    fun provideCharactersUseCaseModule(repo: CharactersListRepo) = CharactersUseCase(repo)
    factory { provideCharactersUseCaseModule(get()) }
}

val characterDetailsUseCaseModule = module {

    fun provideCharacterDetailsUseCaseModule(repo: CharacterDetailRepo) =
        CharacterDetailUseCase(repo)
    single { provideCharacterDetailsUseCaseModule(get()) }
}

val imagesUseCaseModule = module {

    fun provideImagesUseCaseModule(repo: ImageRepo) = ImagesUseCase(repo)
    single { provideImagesUseCaseModule(get()) }
}

val characterServiceModule = module {

    fun provideCharacterServiceModule() = CharacterService()
    single { provideCharacterServiceModule() }
}

val imageServiceModule = module {

    fun provideImageServiceModule() = ImageService()
    single { provideImageServiceModule() }
}

