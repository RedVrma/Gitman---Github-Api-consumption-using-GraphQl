package com.verma.gitman.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.verma.gitman.R
import com.verma.gitman.api.ImGithubService
import com.verma.gitman.repository.GithubRepo
import com.verma.gitman.viewModels.ProfileView.ProfileViewModel
import com.verma.gitman.viewModels.ProfileView.ProfileViewModelFactory
import com.verma.gitsearch.api.ApolloClientInstance

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileView.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileView : Fragment() {

    lateinit var profileViewModel: ProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_view, container, false)

        val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)
        val loginTextView: TextView = view.findViewById(R.id.loginTextView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val bioTextView: TextView = view.findViewById(R.id.bioTextView)
        val followersCountTV: TextView = view.findViewById(R.id.followersCountTV)
        val followingCountTV: TextView = view.findViewById(R.id.followingCountTV)

        val ffClickLayer: View = view.findViewById(R.id.ff_click_layer)


        val client = ApolloClientInstance.getInstance()
        val githubService = ImGithubService(client)

        val repo = GithubRepo(githubService)

        profileViewModel =
            ViewModelProvider(this, ProfileViewModelFactory(repo)).get(ProfileViewModel::class.java)

        ///Fetch profile data from server
        val receivedUsername = arguments?.getString("login")
        profileViewModel.fetchProfileData(receivedUsername!!)


        profileViewModel.profileData.observe(viewLifecycleOwner) {
            if (it != null) {
                val githubUser = it

                Glide.with(view)
                    .load(githubUser.avatarUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(avatarImageView)

                loginTextView.text = githubUser.login
                nameTextView.text = githubUser.name
                bioTextView.text = githubUser.bio
                followersCountTV.text = githubUser.followers.totalCount.toString()
                followingCountTV.text = githubUser.following.totalCount.toString()
            }
        }

        ffClickLayer.setOnClickListener {
            val b = Bundle()
            b.putString("login", receivedUsername)
            findNavController().navigate(R.id.action_profileView_to_followerFollowingView, b)
        }


        return view
    }

}