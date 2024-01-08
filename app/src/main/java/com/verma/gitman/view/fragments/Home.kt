package com.verma.gitman.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verma.gitman.R
import com.verma.gitman.api.ImGithubService
import com.verma.gitman.repository.GithubRepo
import com.verma.gitman.view.adapters.RvSearchResultAdapter
import com.verma.gitman.viewModels.HomeViewModel
import com.verma.gitman.viewModels.HomeViewModelFactory
import com.verma.gitsearch.api.ApolloClientInstance
import schema.github.SearchUsersQuery

class Home : Fragment() {

    lateinit var homeViewModel: HomeViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchButton: ImageView
    private lateinit var searchBar: EditText
    private lateinit var noUsersFoundTV: TextView
    private lateinit var loader: ProgressBar
    private lateinit var adapterSearch: RvSearchResultAdapter
    private lateinit var usersList: List<SearchUsersQuery.AsUser>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val client = ApolloClientInstance.getInstance()
        val githubService = ImGithubService(client)

        val repo = GithubRepo(githubService)
        usersList = ArrayList()

        recyclerView = view.findViewById(R.id.RvSearchResults)
        searchButton = view.findViewById(R.id.searchButton)
        searchBar = view.findViewById(R.id.searchBar)
        noUsersFoundTV = view.findViewById(R.id.noUsersFoundTV)
        loader = view.findViewById(R.id.loader)

        ///Adapter OnclickListener...
        adapterSearch = RvSearchResultAdapter(usersList) {
            val b = Bundle()
            b.putString("login", it)
            findNavController().navigate(R.id.action_home2_to_profileView, b)
        }

        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory(repo)).get(HomeViewModel::class.java)

        homeViewModel.usersList.observe(viewLifecycleOwner) {

            if (!it.isEmpty()) {
                noUsersFoundTV.visibility = View.GONE
                loader.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
                usersList = it
                adapterSearch.updateData(it)
            } else if(it.isEmpty() && !homeViewModel.isLoading) {
                recyclerView.visibility = View.GONE
                loader.visibility = View.GONE
                noUsersFoundTV.visibility = View.VISIBLE
            }
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = adapterSearch
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                // Check if we need to load more data when the last item is visible
                if (lastVisibleItemPosition == totalItemCount - 1 && !homeViewModel.isLoading) {
                    homeViewModel.getUsers("", false)
                }
            }
        })

        searchButton.setOnClickListener {
            loader.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            noUsersFoundTV.visibility = View.GONE

            homeViewModel.getUsers(searchBar.text.toString(), true)
        }

        // Inflate the layout for this fragment
        return view
    }
}