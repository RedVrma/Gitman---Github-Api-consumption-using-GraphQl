package com.verma.gitman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verma.gitman.api.Im_GithubService
import com.verma.gitman.models.GithubUser
import com.verma.gitman.repository.GithubRepo
import com.verma.gitman.view.adapters.RvSearchResultAdapter
import com.verma.gitman.viewModels.MainViewModel
import com.verma.gitman.viewModels.MainViewModelFactory
import com.verma.gitsearch.api.ApolloClientInstance
import schema.github.SearchUsersQuery


class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: ImageView
    private lateinit var searchBar: EditText
    private lateinit var adapterSearch: RvSearchResultAdapter
    private lateinit var usersList:List<SearchUsersQuery.AsUser>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = ApolloClientInstance.getInstance()
        val githubService = Im_GithubService(client)

        val repo = GithubRepo(githubService);
        usersList = ArrayList()

        recyclerView = findViewById(R.id.RvSearchResults)
        searchButton = findViewById(R.id.searchButton)
        searchBar = findViewById(R.id.searchBar)

        adapterSearch =  RvSearchResultAdapter(usersList)

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repo)).get(MainViewModel::class.java)

        mainViewModel.getUsers("red")

        mainViewModel.usersList.observe(this) {
            if (it != null) {
                usersList = it
                adapterSearch.updateData(it)
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterSearch
        }

        searchButton.setOnClickListener {
            mainViewModel.getUsers(searchBar.text.toString())
        }




    }
}