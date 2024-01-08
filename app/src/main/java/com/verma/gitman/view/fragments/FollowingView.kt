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
import com.verma.gitman.view.adapters.RvFollowingAdapter
import com.verma.gitman.viewModels.FollowersView.FollowingViewModel
import com.verma.gitman.viewModels.FollowersView.FollowingViewModelFactory
import com.verma.gitsearch.api.ApolloClientInstance
import schema.github.GetUserFollowingQuery

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "login"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowingView.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowingView : Fragment() {
    private var login: String? = null

    lateinit var viewModel: FollowingViewModel
    private lateinit var usersList: List<GetUserFollowingQuery.Node>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterSearch: RvFollowingAdapter

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
        val view = inflater.inflate(R.layout.fragment_following_view, container, false)

        val receivedUsername = arguments?.getString("login")

        usersList = ArrayList()

        recyclerView = view.findViewById(R.id.RvFollowing)

        ///Adapter OnclickListener...
        adapterSearch = RvFollowingAdapter(usersList) {
            val b = Bundle()
            b.putString("login", it)
            findNavController().navigate(R.id.action_followerFollowingView_to_profileView, b)
        }

        val client = ApolloClientInstance.getInstance()
        val githubService = ImGithubService(client)
        val repo = GithubRepo(githubService)
        viewModel = ViewModelProvider(this, FollowingViewModelFactory(repo)).get(
            FollowingViewModel::class.java
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
            FollowingView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, login)
                }
            }
    }
}