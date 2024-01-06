package com.verma.gitman.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verma.gitman.models.Users
import com.verma.gitman.repository.GithubRepo
import kotlinx.coroutines.launch
import schema.github.SearchUsersQuery

class MainViewModel(private val repo: GithubRepo) : ViewModel() {
    val usersList = MutableLiveData<List<SearchUsersQuery.AsUser>>()

    fun getUsers(q: String) {
        viewModelScope.launch {
            val res = repo.getUsers(q)
            usersList.postValue(res);
        }
    }
}