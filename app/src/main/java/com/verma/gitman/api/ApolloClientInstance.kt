package com.verma.gitsearch.api

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

object ApolloClientInstance {
    ///Add your token here
    val token = ""
    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val original: Request = chain.request()
            val builder: Request.Builder =
                original.newBuilder().method(original.method(), original.body())
            builder.header("Authorization", "bearer $token")
            chain.proceed(builder.build())
        }
        .build()

    fun getInstance(): ApolloClient {
        return ApolloClient.builder()
            .serverUrl("https://api.github.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }
}