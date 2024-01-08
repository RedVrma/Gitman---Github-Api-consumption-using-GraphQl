package com.verma.gitman.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.await
import com.verma.gitsearch.api.GithubService
import schema.github.GetUserFollowersQuery
import schema.github.GetUserFollowingQuery
import schema.github.GetUserProfileQuery
import schema.github.SearchUsersQuery

class ImGithubService(private val apolloClient: ApolloClient) : GithubService {

    override suspend fun getUsers(q: String, endCursor: String?): Response<SearchUsersQuery.Data> {
        return apolloClient.query(SearchUsersQuery(q, Input.optional(endCursor))).await()
    }

    override suspend fun getUserProfile(login: String): Response<GetUserProfileQuery.Data> {
        return apolloClient.query(GetUserProfileQuery(login)).await()
    }

    override suspend fun getUserFollowers(
        login: String,
        endCursor: String?
    ): Response<GetUserFollowersQuery.Data> {
        return apolloClient.query(GetUserFollowersQuery(login, Input.optional(endCursor))).await()
    }

    override suspend fun getUserFollowing(
        login: String,
        endCursor: String?
    ): Response<GetUserFollowingQuery.Data> {
        return apolloClient.query(GetUserFollowingQuery(login, Input.optional(endCursor))).await()
    }
}