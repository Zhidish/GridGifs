package com.homebrew.gifsygrid.network

import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface Service {

    @GET("/v1/gifs/search")
    suspend fun getGifBySearch(
        @Query("api_key") api_key: String="cnwVIVJ20DngG5MaSMKqCMjz86MtfZHs",
        @Query("q") q: String,
        @Query("limit") limit:Int= 8,
        @Query("offset") offset: Int = 0,
        @Query("rating") rating:String="g",
        @Query("lang")  lang : String="en",
        @Query("bundle") bundle:String="messaging_non_clips"
    ): SearchData

    @GET("gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") api_key: String="cnwVIVJ20DngG5MaSMKqCMjz86MtfZHs",
        @Query("offset") offset: Int=0,
        @Query("limit") limit: Int=12
    ): SearchData

    @GET
    suspend fun downloadGif(@Url gifUrl: String): ResponseBody

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
