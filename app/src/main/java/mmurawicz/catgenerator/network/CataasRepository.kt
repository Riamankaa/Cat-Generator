package mmurawicz.catgenerator.network

class CataasRepository (private val apiHelper: ApiHelper) {

    suspend fun getTags() = apiHelper.getTags()
}
