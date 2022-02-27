package com.jarc.marvelcharacterswiki.ui.fragments

import com.jarc.domain.models.Thumbnail

interface CharacterImageListener {

    fun getCharacterImage(imageInfo: Thumbnail,
                          position: Int)
}