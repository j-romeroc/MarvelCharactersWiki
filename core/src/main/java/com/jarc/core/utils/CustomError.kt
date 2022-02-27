package com.jarc.core.utils

class CustomError(private var underLyingError: Throwable, private val originLayer: OriginLayer) :
    Throwable() {

    enum class OriginLayer {
        DATA_LAYER, DOMAIN_LAYER, PRESENTATION_LAYER
    }

    fun getErrorOriginLayer() = originLayer

    fun getUnderlyingError() = underLyingError

    fun getErrorOriginLayerMsg() = when (originLayer) {
        OriginLayer.DOMAIN_LAYER -> {
            "Domain Layer"
        }
        OriginLayer.DATA_LAYER -> {
            "Data Layer"
        }
        OriginLayer.PRESENTATION_LAYER -> {
            "Presentation Layer"
        }
    }

    fun getErrorDetailedMsg(): String = underLyingError.localizedMessage

}





