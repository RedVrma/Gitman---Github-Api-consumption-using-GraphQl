package com.verma.gitsearch.api

import com.apollographql.apollo.api.Response
import schema.github.GetUserFollowersQuery
import schema.github.GetUserFollowingQuery
import schema.github.GetUserProfileQuery
import schema.github.SearchUsersQuery

interface GithubService {
    suspend fun getUsers(q: String, endCursor: String?): Response<SearchUsersQuery.Data>

    suspend fun getUserProfile(login: String): Response<GetUserProfileQuery.Data>

    suspend fun getUserFollowers(
        login: String,
        endCursor: String?
    ): Response<GetUserFollowersQuery.Data>

    suspend fun getUserFollowing(
        login: String,
        endCursor: String?
    ): Response<GetUserFollowingQuery.Data>


}