package com.verma.gitman.viewModels.ProfileView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.verma.gitman.repository.GithubRepo
import kotlinx.coroutines.launch
import schema.github.GetUserProfileQuery

class ProfileViewModel(var repo: GithubRepo) : ViewModel() {

    val profileData = MutableLiveData<GetUserProfileQuery.AsUser?>()
    var loading = MutableLiveData<Boolean>()

    fun fetchProfileData(login: String) {
        loading.postValue(true)
        viewModelScope.launch {
            val res = repo.getUserProfile(login)
            profileData.postValue(res.data?.repositoryOwner?.asUser)
            loading.postValue(false)
        }
    }
}