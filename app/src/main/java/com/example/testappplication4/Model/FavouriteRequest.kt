package com.example.testappplication4.Model

data class FavouriteRequest(
    val created_at: String,
    val id: Int,
    val image: Image,
    val image_id: String,
    val sub_id: String,
    val user_id: String
)