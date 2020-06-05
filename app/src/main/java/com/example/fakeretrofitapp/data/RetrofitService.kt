package com.example.fakeretrofitapp.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {
    // Adds to Retrofit the appearance of log information after executing a request such as the BODY or the HEADERS.
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        // Headers that don't reach the RESTful service.
        val originRequest = chain.request()
        val newRequest = originRequest.newBuilder()
                .header("Interceptor-Header", "xyz")
                .build()
        chain.proceed(newRequest)
    }.addInterceptor(loggingInterceptor).build()

    // Allows nulls to be sent to the RESTful service when the corresponding request is executed.
    private val gson = GsonBuilder().serializeNulls().create()
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}