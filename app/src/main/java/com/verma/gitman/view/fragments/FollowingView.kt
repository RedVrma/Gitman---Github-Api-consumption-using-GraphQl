package com.verma.gitman.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verma.gitman.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "login"

class FollowingView : Fragment() {
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
        val view =  inflater.inflate(R.layout.fragment_following_view, container, false)

        val receivedUsername = arguments?.getString("login")
        Log.d("BROMOSO", "following: ${receivedUsername}")

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FollowingView.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(login: String) =
            FollowingView().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, login)
                }
            }
    }
}