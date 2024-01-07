package com.verma.gitman.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verma.gitman.repository.GithubRepo
import kotlinx.coroutines.launch
import schema.github.SearchUsersQuery

class HomeViewModel(private val repo: GithubRepo) : ViewModel() {
    val usersList = MutableLiveData<List<SearchUsersQuery.AsUser>>()
    var endCursor: String? = null
    var currentQuery = ""
    var isLoading = MutableLiveData<Boolean>()
    var hasNextPage: Boolean? = true
    fun getUsers(
        q: String,
        clearPagination: Boolean,
    ) {
        isLoading.postValue(true)
        if (clearPagination) {
            endCursor = null
            currentQuery = q
            hasNextPage = true
            usersList.postValue(emptyList())
        }
        if (hasNextPage!!) {
            viewModelScope.launch {
                val res = repo.getUsers(currentQuery, endCursor)
                val listOfUsers =
                    res.data?.search?.edges?.mapNotNull { it?.node?.asUser } ?: emptyList()

                val updatedList = mutableListOf<SearchUsersQuery.AsUser>()
                updatedList.addAll(usersList.value ?: emptyList())
                updatedList.addAll(listOfUsers)

                usersList.postValue(updatedList)
                isLoading.postValue(false)
                ///end cursor is used for pagination
                endCursor = res.data?.search?.pageInfo?.endCursor
                hasNextPage = res.data?.search?.pageInfo?.hasNextPage
            }
        }
    }

}