package com.example.fakeretrofitapp

import com.example.fakeretrofitapp.models.Comment
import com.example.fakeretrofitapp.models.Post
import retrofit2.Call
import retrofit2.http.*

interface JsonPlaceHolderApi {
    @GET("posts")
    fun getPosts(
            @Query("userId") userId: Int?,
            @Query("_sort") sort: String,
            @Query("_order") order: String): Call<List<Post>>

    @GET("posts")
    fun getPosts(@QueryMap parameters: Map<String, String>): Call<List<Post>>

    @GET("posts/{id}/comments")
    fun getComments(@Path("id") postId: Int): Call<List<Comment>>

    @GET
    fun getComments(@Url url: String): Call<List<Comment>>

    @POST("posts")
    fun createPost(@Body post: Post): Call<Post>

    @FormUrlEncoded
    @POST("posts")
    fun createPost(
            @Field("userId") userId: Int,
            @Field("title") title: String,
            @Field("body") body: String
    ): Call<Post>

    @FormUrlEncoded
    @POST("posts")
    fun createPost(@FieldMap fields: Map<String, String>): Call<Post>

    @Headers("Static-Header: 123", "Static-Header2: 456")
    @PUT("posts/{id}")
    fun putPost(@Header("Dynamic-Header") header: String,
                @Path("id") id: Int,
                @Body post: Post): Call<Post>

    @PATCH("posts/{id}")
    fun patchPost(@HeaderMap headers: Map<String, String>, @Path("id") id: Int, @Body post: Post): Call<Post>

    @DELETE("posts/{id}")
    fun deletePost(@Path("id") id: Int): Call<Void>
}