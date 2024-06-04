package com.example.quotes

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Quotes", "Bearer $token")
            .build()
        return chain.proceed(newRequest)
    }
}