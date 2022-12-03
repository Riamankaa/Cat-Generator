package mmurawicz.catgenerator.network


class ApiHelper(private val apiService: ApiService) {

    suspend fun getTags() = apiService.getTags()
}
