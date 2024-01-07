package com.verma.gitman.viewModels.ProfileView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.verma.gitman.repository.GithubRepo

class ProfileViewModelFactory(val repo: GithubRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(repo) as T
    }
}