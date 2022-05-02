package com.eesolutions.jeux.lovefinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.eesolutions.jeux.lovefinder.databinding.FragmentMainGameBinding
import com.eesolutions.jeux.lovefinder.viewmodel.MainGameViewModel

class MainGameFragment : Fragment() {
    private lateinit var binding: FragmentMainGameBinding
    private lateinit var viewModel : MainGameViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainGameBinding.inflate(layoutInflater, container, false)

        // init view model
        viewModel = ViewModelProvider(this).get(MainGameViewModel::class.java)

        // subcribe observe for boy character
        viewModel.boyCharacter.observe(this.viewLifecycleOwner) {
            boyCharacter ->
            // moving corrsponding boyViewImage
            with (binding) {
                boyImageView.animate()
                    .x(boyCharacter.x.toFloat())
                    .y(boyCharacter.y.toFloat())
                    .setDuration(10)
                    .withEndAction {
                        //to make sure that it arrives,
                        //but not needed actually these two lines
                        boyImageView.x = boyCharacter.x.toFloat()
                        boyImageView.y = boyCharacter.y.toFloat()
                    }
                    .start()
            }
        }
        return binding.root
    }

}