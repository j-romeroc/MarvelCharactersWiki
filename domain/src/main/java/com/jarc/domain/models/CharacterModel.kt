package com.jarc.domain.models

import com.jarc.core.utils.StoryType


data class CharacterModel(
    val id: Long,
    val name: String,
    val description: String,
    val modified: String,
    val thumbnail: Thumbnail,
    val resourceURI: String,
    val comics: Publishings,
    val series: Publishings,
    val stories: Publishings,
    val events: Publishings,
    val urls: List<URL>
)

data class Publishings(
    val available: Long,
    val collectionURI: String,
    val items: List<Any>,
    val returned: Long
)

data class PublishingItem(
    val resourceURI: String,
    val name: String,
    val type: StoryType? = null
)

data class Thumbnail(
    val path: String,
    val extension: String
)

data class URL(
    val type: String,
    val url: String
)
