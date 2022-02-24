package com.jarc.domain.models

data class CharacterDetailModel(
    val id: Long,
    val name: String,
    val description: String,
    val thumbnail: DetailThumbnail,
    val storiesCount: Long,
    val comicsCount: Long,
    val seriesCount: Long,
    val eventsCount: Long,
    val detailUrl: String)

data class DetailThumbnail (
    val path: String,
    val extension: String
)
