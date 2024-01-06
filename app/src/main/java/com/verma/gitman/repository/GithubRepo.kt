package com.verma.gitman.repository

import com.verma.gitsearch.api.GithubService
import schema.github.SearchUsersQuery

class GithubRepo(private val githubService: GithubService) {

    suspend fun getUsers(q:String): List<SearchUsersQuery.AsUser> {
        return githubService.getUsers(q)

    }
}