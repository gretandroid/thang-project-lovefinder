package com.eesolutions.jeux.lovefinder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.eesolutions.jeux.lovefinder.databinding.FragmentMainGameBinding
import com.eesolutions.jeux.lovefinder.game.model.BoyCharacter
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
        // init once at new game
        if (viewModel.boyCharacter.value === null) {
            with(binding) {
                val width: Int? = context?.resources?.displayMetrics?.widthPixels
                val height: Int? = context?.resources?.displayMetrics?.heightPixels
                boyImageView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                viewModel.setBoyCharacter(BoyCharacter(width!!, height!!, boyImageView.measuredWidth, boyImageView.measuredHeight,100, 50))
            }
        }

        // subcribe observe for boy character
        viewModel.boyCharacter.observe(this.viewLifecycleOwner) {
                boyCharacter ->
            // moving corrsponding boyViewImage
            with (binding) {
                Log.d("App", "observed [x,y] = [${boyCharacter?.x},${boyCharacter?.y}]")
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
                boyCharacter.draw(null)
            }
        }

        if (!viewModel.running) {
            viewModel.running = true
            viewModel.start()
        }

        binding.button.setOnClickListener {
            viewModel.inverse()
        }
        return binding.root
    }

}