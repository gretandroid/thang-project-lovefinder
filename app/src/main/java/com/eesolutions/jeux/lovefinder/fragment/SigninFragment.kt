package com.eesolutions.jeux.lovefinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.eesolutions.jeux.lovefinder.databinding.FragmentSigninBinding
import com.eesolutions.jeux.lovefinder.viewmodel.SigninViewModel

class SigninFragment : Fragment() {
    private lateinit var viewModel: SigninViewModel
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)  // enable menu for this fragment
        val args = SigninFragmentArgs.fromBundle(requireArguments())
        viewModel = ViewModelProvider(this).get(SigninViewModel::class.java);

        binding.model = viewModel
        binding.lifecycleOwner = this
        viewModel.onMessageReveived(args.user)
        binding.signinButton.setOnClickListener {

            // check match password
            val password = binding.passwordSigninText.text.toString()
            if (viewModel.checkPassword(password)) {
                Navigation
                    .findNavController(binding.root)
                    .navigate(SigninFragmentDirections.actionSigninFragmentToStartGameFragment(viewModel.user.value!!))
            }

        }
        return binding.root
    }

}