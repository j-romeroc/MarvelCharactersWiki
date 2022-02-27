package com.jarc.data.entities

data class CharactersRawResponse(
    val data: ResultsRawResponse
) {
    fun mapToEntity() = this.data.results
}

data class ResultsRawResponse(
    val results: List<CharacterEntity>
)
