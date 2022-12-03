package mmurawicz.catgenerator.network

import retrofit2.http.GET

interface ApiService {
    @GET("tags")
    suspend fun getTags(): List<String>
}
