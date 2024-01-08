package com.verma.gitman.viewModels.FollowersView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verma.gitman.repository.GithubRepo
import kotlinx.coroutines.launch
import schema.github.GetUserFollowersQuery

class FollowersViewModel(private val repo: GithubRepo) : ViewModel() {

    val usersList = MutableLiveData<List<GetUserFollowersQuery.Node>>()
    var endCursor: String? = null
    var isLoading = MutableLiveData<Boolean>()
    var hasNextPage: Boolean? = true
    fun getUsers(
        q: String,
    ) {
        isLoading.postValue(true)
        if (hasNextPage!!) {
            viewModelScope.launch {
                val res = repo.getUserFollowers(q, endCursor)

                val listOfUsers =
                    res.data?.repositoryOwner?.asUser?.followers?.nodes?.mapNotNull { it }
                        ?: emptyList()

                val updatedList = mutableListOf<GetUserFollowersQuery.Node>()
                updatedList.addAll(usersList.value ?: emptyList())
                updatedList.addAll(listOfUsers)

                usersList.postValue(updatedList)
                isLoading.postValue(false)
                ///end cursor is used for pagination
                endCursor = res.data?.repositoryOwner?.asUser?.followers?.pageInfo?.endCursor
                hasNextPage = res.data?.repositoryOwner?.asUser?.followers?.pageInfo?.hasNextPage
            }
        }
    }
}