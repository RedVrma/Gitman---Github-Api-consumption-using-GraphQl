package com.verma.gitman.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.verma.gitman.R
import com.verma.gitman.view.adapters.FfViewPagerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowerFollowingView.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowerFollowingView : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var backButton: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_follower_following_view, container, false)

        viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)
        backButton = view.findViewById(R.id.backButton)

        val receivedUsername = arguments?.getString("login")

        val fragments = listOf(
            FollowersView.newInstance(receivedUsername ?: ""),
            FollowingView.newInstance(receivedUsername ?: "")
        )
        val adapter = FfViewPagerAdapter(requireActivity(), fragments)

        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()

        backButton.setOnClickListener{
            findNavController().popBackStack()
        }

        return view
    }

}