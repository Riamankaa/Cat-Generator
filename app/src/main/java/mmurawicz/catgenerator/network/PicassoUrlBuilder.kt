package mmurawicz.catgenerator.network

object PicassoUrlBuilder {

    fun getImageUrl(tag: String, filter: String): String {
        var url = "https://cataas.com/cat"
        if (tag.isNotEmpty()) {
            url += "/"
        }
        url += "$tag?filter=$filter"
        return url
    }
}
