package com.verma.gitman.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.verma.gitman.R
import com.verma.gitman.api.ImGithubService
import com.verma.gitman.repository.GithubRepo
import com.verma.gitman.view.adapters.RvFollowerAdapter
import com.verma.gitman.viewModels.FollowersView.FollowersViewModel
import com.verma.gitman.viewModels.FollowersView.FollowersViewModelFactory
import com.verma.gitsearch.api.ApolloClientInstance
import schema.github.GetUserFollowersQuery

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "login"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersView.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersView : Fragment() {
    private var login: String? = null

    lateinit var viewModel: FollowersViewModel
    private lateinit var usersList: List<GetUserFollowersQuery.Node>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterSearch: RvFollowerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            login = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_followers_view, container, false)
        val receivedUsername = arguments?.getString("login")
        usersList = ArrayList()

        recyclerView = view.findViewById(R.id.RvFollowers)

        ///Adapter OnclickListener...
        adapterSearch = RvFollowerAdapter(usersList) {
            val b = Bundle()
            b.putString("login", it)
            findNavController().navigate(R.id.action_followerFollowingView_to_profileView, b)
        }

        val client = ApolloClientInstance.getInstance()
        val githubService = ImGithubService(client)
        val repo = GithubRepo(githubService)
        viewModel = ViewModelProvider(this, FollowersViewModelFactory(repo)).get(
            FollowersViewModel::class.java
        )

        ///Calling to fetch first 10 followers to show in list
        viewModel.getUsers(receivedUsername!!)

        viewModel.usersList.observe(viewLifecycleOwner) {
            if (it != null) {
                usersList = it
                adapterSearch.updateData(it)
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
                if (lastVisibleItemPosition == totalItemCount - 1 && !viewModel.isLoading.value!!) {
                    viewModel.getUsers(receivedUsername)
                }
            }
        })

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(login: String) =
            FollowersView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, login)
                }
            }
    }
}