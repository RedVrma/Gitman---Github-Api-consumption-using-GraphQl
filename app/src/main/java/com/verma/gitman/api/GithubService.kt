package com.verma.gitsearch.api

import com.apollographql.apollo.api.Response
import schema.github.GetUserProfileQuery
import schema.github.SearchUsersQuery

interface GithubService {
    suspend fun getUsers(q: String, endCursor: String?) : Response<SearchUsersQuery.Data>

    suspend fun getUserProfile(login:String) : Response<GetUserProfileQuery.Data>
}