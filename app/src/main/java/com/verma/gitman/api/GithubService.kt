package com.verma.gitsearch.api

import schema.github.SearchUsersQuery

interface GithubService {
    suspend fun getUsers(q:String) : List<SearchUsersQuery.AsUser>
}