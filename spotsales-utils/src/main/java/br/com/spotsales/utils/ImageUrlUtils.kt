package br.com.spotsales.utils

object ImageUrlUtils {

    private const val PRODUCT_ICON_DEFAULT_PARAMS = "/fit-in/100x/filters:background_color(FFFFFF):format(jpeg)" +
            ":quality(65):strip_icc()/"
    private const val EVENT_IMAGE_DEFAULT_PARAMS = "/fit-in/300x110/filters:background_color(FFFFFF):quality(80)" +
            ":grayscale():format(jpeg):strip_icc()/"

    private var productIconFilter: String = PRODUCT_ICON_DEFAULT_PARAMS
    private var eventIconFilter: String = EVENT_IMAGE_DEFAULT_PARAMS

    fun setFilters(productIcon: String, eventIcon: String) {
        eventIconFilter = eventIcon
        productIconFilter = productIcon
    }

    fun applyInProductIcon(iconUrl: String): String = buildFilteredUrl(iconUrl, productIconFilter)

    fun applyInEventIcon(imageUrl: String): String = buildFilteredUrl(imageUrl, eventIconFilter)

    private fun buildFilteredUrl(url: String, filterParams: String): String {
        if (url.isNotEmpty()) {
            val tokens = url.split("://", "/")

            if (tokens.size > 2) {
                return tokens[0] + "://" + tokens[1] + filterParams + tokens.subList(2, tokens.size).joinToString("/")
            }
        }

        return url
    }
}
