package com.jarc.core.utils


abstract class AspectRatio {

    enum class ImageSize {
        SMALL,
        MEDIUM,
        LARGE,
        XLARGE,
        FANTASTIC,
        UNCANNY,
        INCREDIBLE,
        AMAZING
    }

    enum class Origin {
        LIST, DETAIL
    }

    abstract fun getSize(type: ImageSize): String
}

object PortraitAspectRatio : AspectRatio() {

    override fun getSize(type: ImageSize) =
        when (type) {
            ImageSize.SMALL -> "portrait_small"
            ImageSize.MEDIUM -> "portrait_medium"
            ImageSize.XLARGE -> "portrait_xlarge"
            ImageSize.FANTASTIC -> "portrait_fantastic"
            ImageSize.UNCANNY -> "portrait_uncanny"
            ImageSize.INCREDIBLE -> "portrait_incredible"
            else -> ""
        }
}

object StandardAspectRatio : AspectRatio() {

    override fun getSize(type: ImageSize) =
        when (type) {
            ImageSize.SMALL -> "standard_small"
            ImageSize.MEDIUM -> "standard_medium"
            ImageSize.LARGE -> "standard_large"
            ImageSize.XLARGE -> "standard_xlarge"
            ImageSize.FANTASTIC -> "standard_fantastic"
            ImageSize.AMAZING -> "standard_amazing"
            else -> ""
        }
}

object LandscapeAspectRatio : AspectRatio() {

    override fun getSize(type: ImageSize) =
        when (type) {
            ImageSize.SMALL -> "landscape_small"
            ImageSize.MEDIUM -> "landscape_medium"
            ImageSize.LARGE -> "landscape_large"
            ImageSize.XLARGE -> "landscape_xlarge"
            ImageSize.AMAZING -> "landscape_amazing"
            ImageSize.INCREDIBLE -> "landscape_incredible"
            else -> ""
        }
}
