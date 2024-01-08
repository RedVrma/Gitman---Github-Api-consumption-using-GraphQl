package com.verma.gitman.viewModels

import android.util.Log
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
    var isLoading:Boolean = false
    var hasNextPage: Boolean? = true
    fun getUsers(
        q: String,
        clearPagination: Boolean,
    ) {
        if (clearPagination) {
            endCursor = null
            currentQuery = q
            hasNextPage = true
            usersList.postValue(emptyList())
        }
        if (hasNextPage!! && !isLoading) {
            viewModelScope.launch {
                isLoading = true
                val res = repo.getUsers(currentQuery, endCursor)
                if(res.data?.search?.edges?.isEmpty() == true){
                    isLoading = false
                }
                val listOfUsers =
                    res.data?.search?.edges?.mapNotNull { it?.node?.asUser } ?: emptyList()

                val updatedList = mutableListOf<SearchUsersQuery.AsUser>()
                updatedList.addAll(usersList.value ?: emptyList())
                updatedList.addAll(listOfUsers)

                usersList.postValue(updatedList)
                isLoading = false
                ///end cursor is used for pagination
                endCursor = res.data?.search?.pageInfo?.endCursor
                hasNextPage = res.data?.search?.pageInfo?.hasNextPage
            }
        }
    }

}