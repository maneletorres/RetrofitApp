package com.example.fakeretrofitapp.models

import com.google.gson.annotations.SerializedName

class Comment(@SerializedName("postId") val postId: Int?,
              @SerializedName("id") val id: Int?,
              @SerializedName("name") val name: String?,
              @SerializedName("email") val email: String?,
              @SerializedName("body") val body: String?
)