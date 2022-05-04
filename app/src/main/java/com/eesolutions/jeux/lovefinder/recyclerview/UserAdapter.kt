package com.eesolutions.jeux.lovefinder.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eesolutions.jeux.lovefinder.R
import com.eesolutions.jeux.lovefinder.databinding.RowUsersBinding
import com.eesolutions.jeux.lovefinder.model.User

class UserAdapter(private val userList : List<User>?, private val listener : UserAdapterListener) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(R.layout.row_users, parent, false);
        return ViewHolder(root);
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            orderTextView.setText(userList?.get(position)?.id.toString() ?: "");
            loginTextView.setText(userList?.get(position)?.login?: "");
            rowLayout.setOnClickListener {
                listener.onClick(it, userList?.get(position))
            }
        }

    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding = RowUsersBinding.bind(itemView);
    }

    interface UserAdapterListener {
        fun onClick(view: View?, user: User?)
    }
}
