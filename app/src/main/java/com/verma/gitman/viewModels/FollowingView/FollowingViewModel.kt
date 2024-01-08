package com.verma.gitman.viewModels.FollowersView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verma.gitman.repository.GithubRepo
import kotlinx.coroutines.launch
import schema.github.GetUserFollowingQuery

class FollowingViewModel(private val repo: GithubRepo) : ViewModel() {

    val usersList = MutableLiveData<List<GetUserFollowingQuery.Node>>()
    var endCursor: String? = null
    var isLoading = MutableLiveData<Boolean>()
    var hasNextPage: Boolean? = true
    fun getUsers(
        q: String,
    ) {
        isLoading.postValue(true)
        if (hasNextPage!!) {
            viewModelScope.launch {
                val res = repo.getUserFollowing(q, endCursor)

                val listOfUsers =
                    res.data?.repositoryOwner?.asUser?.following?.nodes?.mapNotNull { it }
                        ?: emptyList()

                val updatedList = mutableListOf<GetUserFollowingQuery.Node>()
                updatedList.addAll(usersList.value ?: emptyList())
                updatedList.addAll(listOfUsers)

                usersList.postValue(updatedList)
                isLoading.postValue(false)
                ///end cursor is used for pagination
                endCursor = res.data?.repositoryOwner?.asUser?.following?.pageInfo?.endCursor
                hasNextPage = res.data?.repositoryOwner?.asUser?.following?.pageInfo?.hasNextPage
            }
        }
    }
}