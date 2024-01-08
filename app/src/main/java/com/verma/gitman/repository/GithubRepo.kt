package com.verma.gitman.repository

import com.apollographql.apollo.api.Response
import com.verma.gitsearch.api.GithubService
import schema.github.GetUserFollowersQuery
import schema.github.GetUserFollowingQuery
import schema.github.GetUserProfileQuery
import schema.github.SearchUsersQuery

class GithubRepo(private val githubService: GithubService) {

    suspend fun getUsers(q: String, endCursor: String?): Response<SearchUsersQuery.Data> {
        return githubService.getUsers(q, endCursor)

    }

    suspend fun getUserProfile(login: String): Response<GetUserProfileQuery.Data> {
        return githubService.getUserProfile(login)
    }

    suspend fun getUserFollowers(
        login: String,
        endCursor: String?
    ): Response<GetUserFollowersQuery.Data> {
        return githubService.getUserFollowers(login, endCursor)
    }

    suspend fun getUserFollowing(
        login: String,
        endCursor: String?
    ): Response<GetUserFollowingQuery.Data> {
        return githubService.getUserFollowing(login, endCursor)
    }


}