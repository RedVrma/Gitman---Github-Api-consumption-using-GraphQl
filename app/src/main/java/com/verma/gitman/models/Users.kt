package com.verma.gitman.models

data class Users(
    val total_count: Int,
    val incomplete_results: Boolean,
    val items: List<GithubUser>
)
