package com.example.testappplication4.Common

import com.example.testappplication4.Model.BreedItem
import com.example.testappplication4.Model.FavouriteRequest
import java.util.ArrayList

object Common {
    fun findDogByNum(num: Int): BreedItem? {
        for (dog in dogList)
            if (dog.id == num)
                return dog
        return null
    }
    fun findFavByNum(num: Int): FavouriteRequest? {
        for (fav in favList)
            if (fav.id == num)
                return fav
        return null
    }


    val KEY_VOTE_PAGE = "vote_position"
    var favList:List<FavouriteRequest> = ArrayList()
    var dogList:List<BreedItem> = ArrayList()
    var KEY_ENABLE_HOME = "position"
    var KEY_ENABLE_FAV = "fav_position"
    var SUB_ID = "sanjai"
    var API_KEY = "75cc697c-b73c-42bb-a49d-a789e4992249"
    var vote_position = 0
    var fav_position = 0
    var home_position = 0
}