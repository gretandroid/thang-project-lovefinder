package com.eesolutions.jeux.lovefinder.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eesolutions.jeux.lovefinder.R
import com.eesolutions.jeux.lovefinder.databinding.RowHeaderBinding
import com.eesolutions.jeux.lovefinder.databinding.RowUsersBinding
import com.eesolutions.jeux.lovefinder.model.User

private const val TYPE_HEADER = 0
private const val TYPE_ITEM = 1
class UserAdapter(private val userList : List<User>?, private val listener : UserAdapterListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_HEADER -> {
                val root = inflater.inflate(R.layout.row_header, parent, false);
                return HeaderViewHolder(root);
            }
            else  -> {
                    val root = inflater.inflate(R.layout.row_users, parent, false);
                    return ItemViewHolder(root);
                }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            with(holder.binding) {
                orderTextView.setText(userList?.get(position-1)?.score.toString() ?: "");
                loginTextView.setText(userList?.get(position-1)?.login?: "");
                rowLayout.setOnClickListener {
                    listener.onClick(it, userList?.get(position-1))
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        when(position) {
            0 -> return TYPE_HEADER
            else -> return TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return (userList?.size ?: 0) + 1;
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RowUsersBinding.bind(itemView);
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RowHeaderBinding.bind(itemView);
    }

    interface UserAdapterListener {
        fun onClick(view: View?, user: User?)
    }
}
