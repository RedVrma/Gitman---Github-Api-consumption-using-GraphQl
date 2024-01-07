package com.verma.gitman.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verma.gitman.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "login"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowersView.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowersView : Fragment() {
    private var login: String? = null

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
        val view =  inflater.inflate(R.layout.fragment_followers_view, container, false)

        val receivedUsername = arguments?.getString("login")

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