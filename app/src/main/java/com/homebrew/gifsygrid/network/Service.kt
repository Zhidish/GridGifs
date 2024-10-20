package com.homebrew.gifsygrid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {

    @GET("/v1/gifs/search")
    suspend fun getGifBySearch(
        @Query("api_key") api_key: String="bHjmyGtL9zIg4HBCGa0qDxcvT47C9XDK",
        @Query("q") q: String,
        @Query("limit") limit:String="4",
        @Query("offset") offset:String="0",
        @Query("rating") rating:String="g",
        @Query("lang")  lang : String="en",
        @Query("bundle") bundle:String="messaging_non_clips"
    ): SearchData


    companion object {
        private const val BASE_URL =
            "https://api.giphy.com"

        fun create(): Service {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(Service::class.java)
        }
    }
}
