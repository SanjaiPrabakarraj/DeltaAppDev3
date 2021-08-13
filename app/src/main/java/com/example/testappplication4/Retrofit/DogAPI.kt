package com.example.testappplication4.Retrofit

import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.Model.FavouriteRequest
import com.example.testappplication4.Model.FavouriteResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Query
import java.io.File
import java.util.HashMap

interface DogAPI {
    @Headers("x-api-key: 75cc697c-b73c-42bb-a49d-a789e4992249")
    @GET("/v1/breeds")
    suspend fun getDogs(): Response<List<BreedItem>>

    @Headers("x-api-key: 75cc697c-b73c-42bb-a49d-a789e4992249")
    @GET("/v1/favourites")
    suspend fun getFavourites(@Query("sub_id") sub_id: String): Response<List<FavouriteRequest>>

    @Headers("Content-Type: application/json", "x-api-key: 75cc697c-b73c-42bb-a49d-a789e4992249")
    @PUT("/v1/favourites")
    fun addFavorite(
        @Body image_id: String, sub_id: String
    ):Call<FavouriteResponse>

    @Multipart
    @POST("/v1/images/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("sub_id") sub_id: RequestBody
    )
}