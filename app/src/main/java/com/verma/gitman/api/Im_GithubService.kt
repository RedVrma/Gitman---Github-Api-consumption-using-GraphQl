package com.verma.gitman.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.verma.gitsearch.api.GithubService
import schema.github.SearchUsersQuery

class Im_GithubService(private val apolloClient: ApolloClient) : GithubService {

    override suspend fun getUsers(q: String): List<SearchUsersQuery.AsUser> {
        val user = apolloClient.query(SearchUsersQuery(q)).await()
        return user.data?.search?.edges?.mapNotNull { it?.node?.asUser } ?: emptyList()
    }
}