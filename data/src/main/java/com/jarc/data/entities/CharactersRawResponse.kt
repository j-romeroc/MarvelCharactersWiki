package com.jarc.data.entities

data class CharactersRawResponse(
    val data: ResultsRawResponse
){
    fun mapToData() = this.data.results
}

data class ResultsRawResponse(
    val results: List<CharacterData>
)
