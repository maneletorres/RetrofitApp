package com.example.fakeretrofitapp

import com.example.fakeretrofitapp.data.RetrofitService

/**
 * Repository class which creates the instance of the retrofit service and initiates the API call,
 * our method for this call should be a suspend function as it will later be called through a
 * coroutine.
 */
class Repository {
    var client = RetrofitService.createService(JsonPlaceHolderApi::class.java)

    //simplified version of the retrofit call that comes from support with coroutines
    //Note that this does NOT handle errors, to be added
    suspend fun getData(token : Map<String, String>) = client.getPosts(token)
}