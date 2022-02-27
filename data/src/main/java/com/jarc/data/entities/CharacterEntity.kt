package com.jarc.data.entities

import com.jarc.core.utils.StoryType
import com.jarc.domain.models.*

data class CharacterEntity(
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: Comics,
    val series: Comics,
    val stories: Stories,
    val events: Comics,
    val urls: List<URL>
) {

    fun mapEntityToCharacterModel() =
        CharacterModel(
            id = this.id,
            name = this.name,
            description = this.description,
            modified = this.modified,
            thumbnail = com.jarc.domain.models.Thumbnail(
                this.thumbnail.path,
                this.thumbnail.extension
            ),
            resourceURI = this.resourceURI,
            comics = Publishings(
                available = this.comics.available,
                collectionURI = this.comics.collectionURI,
                items = this.comics.items.map { it.mapComicItemsToDomain() },
                returned = this.comics.returned
            ),
            series = Publishings(
                available = this.series.available,
                collectionURI = this.series.collectionURI,
                items = this.series.items.map { it.mapComicItemsToDomain() },
                returned = this.series.returned
            ),
            stories = Publishings(
                available = this.stories.available,
                collectionURI = this.stories.collectionURI,
                items = this.stories.items.map { it.mapStoriesItemsToDomain() },
                returned = this.stories.returned
            ),
            events = Publishings(
                available = this.events.available,
                collectionURI = this.events.collectionURI,
                items = this.events.items.map { it.mapComicItemsToDomain() },
                returned = this.events.returned
            ),
            urls = this.urls.map { it.mapUrls() }
        )

    fun mapEntityToCharacterDetailModel() =
        CharacterDetailModel(
            id = this.id,
            name = this.name,
            description = this.description,
            thumbnail = DetailThumbnail(
                path = this.thumbnail.path,
                extension = this.thumbnail.extension
            ),
            storiesCount = this.stories.available,
            seriesCount = this.series.available,
            comicsCount = this.comics.available,
            eventsCount = this.events.available,
            detailUrl = this.urls.find { it.type == "detail" }?.url ?: ""
        )

}

data class Comics(
    val available: Long,
    val collectionURI: String,
    val items: List<ComicsItem>,
    val returned: Long
)

data class ComicsItem(
    val resourceURI: String,
    val name: String
) {
    fun mapComicItemsToDomain() = PublishingItem(this.resourceURI, this.name)
}

data class Stories(
    val available: Long,
    val collectionURI: String,
    val items: List<StoriesItem>,
    val returned: Long
)

data class StoriesItem(
    val resourceURI: String,
    val name: String,
    val type: StoryType
) {
    fun mapStoriesItemsToDomain() = PublishingItem(this.resourceURI, this.name, this.type)
}

data class Thumbnail(
    val path: String,
    val extension: String
)

data class URL(
    val type: String,
    val url: String
) {

    fun mapUrls() =
        com.jarc.domain.models.URL(
            type = this.type,
            url = this.url
        )

}

