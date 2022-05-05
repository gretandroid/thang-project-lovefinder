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
import com.eesolutions.jeux.lovefinder.databinding.FragmentSignupBinding
import com.eesolutions.jeux.lovefinder.game.util.SettingHelper
import com.eesolutions.jeux.lovefinder.viewmodel.SigninViewModel
import com.eesolutions.jeux.lovefinder.viewmodel.SignupViewModel

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)  // enable menu for this fragment
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java);

        binding.model = viewModel
        binding.lifecycleOwner = this
        binding.signupButton.setOnClickListener {
            // check match password
            val password = binding.passwordSignupText.text.toString()
            val login = binding.loginSignupText.text.toString()
            viewModel.signup(login, password, SettingHelper.getDeviceId(this.requireContext())) {
                Navigation
                    .findNavController(binding.root)
                    .navigate(SignupFragmentDirections.actionSignupFragmentToLandPageFragment())
            }
        }
        return binding.root
    }

}