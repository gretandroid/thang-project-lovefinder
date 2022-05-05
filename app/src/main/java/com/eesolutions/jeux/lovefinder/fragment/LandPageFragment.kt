package com.eesolutions.jeux.lovefinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eesolutions.jeux.lovefinder.databinding.FragmentLandPageBinding
import com.eesolutions.jeux.lovefinder.model.User
import com.eesolutions.jeux.lovefinder.recyclerview.UserAdapter
import com.eesolutions.jeux.lovefinder.viewmodel.LandPageViewModel

class LandPageFragment : Fragment(), UserAdapter.UserAdapterListener {

    private lateinit var viewModel: LandPageViewModel
    private lateinit var binding: FragmentLandPageBinding
    private val userList = mutableListOf<User>();
    private var adapter: UserAdapter? = null;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLandPageBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)  // enable menu for this fragment
        // init/get view model
        viewModel = ViewModelProvider(this).get(LandPageViewModel::class.java);

        // add line separation for user recycler view
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.getContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        // subcribe update UI
        viewModel.userList.observe(this.viewLifecycleOwner) { users ->
            userList.clear();
            userList.addAll(users.sortedByDescending { it.score });

            // make recycler view visible
            if (!userList.isEmpty()) {
                binding.recyclerView.visibility = View.VISIBLE
            }

            if (binding.recyclerView.adapter === null) {
                adapter = UserAdapter(userList, this);
                binding.recyclerView.layoutManager = LinearLayoutManager(this.context);
                binding.recyclerView.adapter = adapter;
            } else {
                adapter?.notifyDataSetChanged();
            }
        }
        viewModel.error.observe(this.viewLifecycleOwner) { message ->
            binding.errorTextView?.setText(message);
            binding.errorTextView?.visibility = View.VISIBLE;
        }
        viewModel.isVisible.observe(this.viewLifecycleOwner) { visible ->
            if (visible) {
//                Log.d("App", "$visible")
//                Log.d("App-d", "${binding.landPageProgressBar?.visibility}")
                binding.landPageProgressBar?.visibility = View.VISIBLE;
//                Log.d("App-d", "${binding.landPageProgressBar?.visibility}")
            } else {
//                Log.d("App", "$visible")
                binding.landPageProgressBar?.visibility = View.GONE;
            }
        }

        // subcribe button
        binding.subcribeButton.setOnClickListener {
            Navigation
                .findNavController(binding.root)
                .navigate(LandPageFragmentDirections.actionLandPageFragmentToSignupFragment())
        }

        return binding.root;
    }

    override fun onClick(view: View?, user: User?) {
        Navigation
            .findNavController(binding.root)
            .navigate(LandPageFragmentDirections.actionLandPageFragmentToSigninFragment(user!!))
    }

}