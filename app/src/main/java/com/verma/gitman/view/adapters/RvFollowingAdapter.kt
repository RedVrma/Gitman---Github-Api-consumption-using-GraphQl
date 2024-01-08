package com.verma.gitman.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.verma.gitman.R
import schema.github.GetUserFollowingQuery

class RvFollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
    private val loginTextView: TextView = itemView.findViewById(R.id.loginTextView)
    private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)

    fun bind(githubUser: GetUserFollowingQuery.Node) {
        Glide.with(itemView)
            .load(githubUser.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(avatarImageView)

        loginTextView.text = githubUser.login
        nameTextView.text = githubUser.name

        // Bind other TextViews with corresponding data if needed
    }

}

class RvFollowingAdapter(
    private var list: List<GetUserFollowingQuery.Node>,
    private val itemClickCallback: ((String) -> Unit)? = null
) : RecyclerView.Adapter<RvFollowingViewHolder>() {
    fun updateData(newList: List<GetUserFollowingQuery.Node>) {
        list = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvFollowingViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_github_user_follower_following, parent, false)
        return RvFollowingViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RvFollowingViewHolder, position: Int) {
        holder.bind(list[position])

        holder.itemView.setOnClickListener {
            itemClickCallback?.invoke(list[position].login)
        }
    }

}