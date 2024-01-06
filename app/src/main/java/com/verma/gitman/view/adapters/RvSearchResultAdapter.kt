package com.verma.gitman.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.verma.gitman.R
import com.verma.gitman.models.GithubUser
import schema.github.SearchUsersQuery

class RvSearchResultViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){

    private val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
    private val loginTextView: TextView = itemView.findViewById(R.id.loginTextView)
    private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    private val bioTextView: TextView = itemView.findViewById(R.id.bioTextView)
    private val followersCountTV: TextView = itemView.findViewById(R.id.followersCountTV)
    private val followingCountTV: TextView = itemView.findViewById(R.id.followingCountTV)

    fun bind(githubUser: SearchUsersQuery.AsUser) {
        Glide.with(itemView)
            .load(githubUser.avatarUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(avatarImageView)

        loginTextView.text = githubUser.login
        nameTextView.text = githubUser.name
        bioTextView.text = githubUser.bio
        followersCountTV.text = githubUser.followers.totalCount.toString()
        followingCountTV.text = githubUser.following.totalCount.toString()

        // Bind other TextViews with corresponding data if needed
    }

}

class RvSearchResultAdapter(private var list:List<SearchUsersQuery.AsUser>) : RecyclerView.Adapter<RvSearchResultViewHolder>() {
    fun updateData(newList: List<SearchUsersQuery.AsUser>) {
        list = newList
        notifyDataSetChanged();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvSearchResultViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_github_user,parent,false)
        return RvSearchResultViewHolder(itemView);
    }

    override fun getItemCount(): Int {
        return list.size;
    }

    override fun onBindViewHolder(holder: RvSearchResultViewHolder, position: Int) {
        holder.bind(list[position])
    }

}