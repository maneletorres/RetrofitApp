package com.example.fakeretrofitapp.models

import com.google.gson.annotations.SerializedName

class Post(@field:SerializedName("userId") val userId: Int?,
           @field:SerializedName("title") val title: String?,
           @field:SerializedName("body") val body: String?) {
    @SerializedName("id")
    val id: Int? = null
}