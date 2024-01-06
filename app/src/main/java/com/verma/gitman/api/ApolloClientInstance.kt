package com.verma.gitsearch.api

import com.apollographql.apollo.ApolloClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

object ApolloClientInstance {
    val token = "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"
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