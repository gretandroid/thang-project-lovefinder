package com.eesolutions.jeux.lovefinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.eesolutions.jeux.lovefinder.R
import com.eesolutions.jeux.lovefinder.databinding.FragmentSigninBinding
import com.eesolutions.jeux.lovefinder.databinding.FragmentStartGameBinding
import com.eesolutions.jeux.lovefinder.viewmodel.SigninViewModel
import com.eesolutions.jeux.lovefinder.viewmodel.StartGameViewModel

class StartGameFragment : Fragment() {
    private lateinit var viewModel: StartGameViewModel
    private lateinit var binding: FragmentStartGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStartGameBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)  // enable menu for this fragment
        val args = StartGameFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(this).get(StartGameViewModel::class.java);

        binding.model = viewModel
        binding.lifecycleOwner = this
        viewModel.onMessageReveived(args.user)

        binding.newGameButton.setOnClickListener {
            Navigation
                .findNavController(binding.root)
                .navigate(StartGameFragmentDirections.actionStartGameFragmentToMainGameFragment(viewModel.user.value!!, true))

        }

        binding.resumeGameButton.setOnClickListener {
            Navigation
                .findNavController(binding.root)
                .navigate(StartGameFragmentDirections.actionStartGameFragmentToMainGameFragment(viewModel.user.value!!, false))

        }
        return binding.root
    }

}