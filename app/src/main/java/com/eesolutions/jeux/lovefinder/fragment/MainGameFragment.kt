package com.eesolutions.jeux.lovefinder.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.eesolutions.jeux.lovefinder.databinding.FragmentMainGameBinding
import com.eesolutions.jeux.lovefinder.game.model.BoyCharacter
import com.eesolutions.jeux.lovefinder.game.model.GirlCharater
import com.eesolutions.jeux.lovefinder.game.model.MatchObject
import com.eesolutions.jeux.lovefinder.viewmodel.MainGameViewModel
import com.eesolutions.jeux.lovefinder.viewmodel.StartGameViewModel

class MainGameFragment : Fragment() {
    private lateinit var binding: FragmentMainGameBinding
    private lateinit var viewModel : MainGameViewModel
    private lateinit var girlImageViews : MutableList<ImageView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainGameBinding.inflate(layoutInflater, container, false)

        val args = MainGameFragmentArgs.fromBundle(requireArguments())

        // init view model
        viewModel = ViewModelProvider(this).get(MainGameViewModel::class.java)

        // init once at new game
        val width: Int? = context?.resources?.displayMetrics?.widthPixels
        val height: Int? = context?.resources?.displayMetrics?.heightPixels
        if (viewModel.boyCharacter.value === null) {
            with(binding) {
                boyImageView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                viewModel.initBoyCharacter(BoyCharacter(0, width!!, height!!, boyImageView.measuredWidth, boyImageView.measuredHeight,width!!/2, height!!/2))
            }
        }

        if (viewModel.girlCharaterList.value === null) {
            with(binding) {
                girl1ImageView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                girl2ImageView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                girl3ImageView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                val girlList = listOf(
                    GirlCharater(width!!, height!!, girl1ImageView.measuredWidth, girl1ImageView.measuredHeight, 200, 100, (-10..10).random(), (-7..7).random()),
                    GirlCharater(width!!, height!!, girl2ImageView.measuredWidth, girl1ImageView.measuredHeight, width!! - 150, height!!- 200, (-5..5).random(), (-12..12).random()),
                    GirlCharater(width!!, height!!, girl3ImageView.measuredWidth, girl1ImageView.measuredHeight, width!! - 250, 300, (-6..6).random(), (-20..20).random())
                )
                girlImageViews = mutableListOf(girl1ImageView, girl2ImageView, girl3ImageView)
                viewModel.initGrilCharacters(girlList)
            }
        }

        if (viewModel.matchObject.value === null) {
            with(binding) {
                matchImageView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                viewModel.initMatchObject(MatchObject(300, 500))
                matchImageView.visibility = View.INVISIBLE
            }
        }

        // subcribe observe for boy character
        viewModel.boyCharacter.observe(this.viewLifecycleOwner) {
                boyCharacter ->
            // moving corrsponding boyViewImage
            with (binding) {
//                Log.d("App", "observed [x,y] = [${boyCharacter?.x},${boyCharacter?.y}]")
                boyImageView.setImageResource(boyCharacter.getCurrentImage())
                boyImageView.animate()
                    .x(boyCharacter.x.toFloat())
                    .y(boyCharacter.y.toFloat())
                    .setDuration(10)
                    .withEndAction {
                        //to make sure that it arrives,
                        //but not needed actually these two lines
//                        boyImageView.x = boyCharacter.x.toFloat()
//                        boyImageView.y = boyCharacter.y.toFloat()
                    }
                    .start()
                boyCharacter.checkPoint()
            }
        }

        // subcribe observe for girl characters
        viewModel.girlCharaterList.observe (this.viewLifecycleOwner) {
                girlList ->
            var currentGirlImageView : ImageView
            with(binding) {
                girlList.forEach {
                    currentGirlImageView = girlImageViews[girlList.indexOf(it)]
                    if (currentGirlImageView !== null) {
                        currentGirlImageView.setImageResource(it.getCurrentImage())
                        currentGirlImageView.animate()
                            .x(it.x.toFloat())
                            .y(it.y.toFloat())
                            .setDuration(10)
                            .withEndAction {
                                //to make sure that it arrives,
                                //but not needed actually these two lines
//                            currentGirlImageView.x = it.x.toFloat()
//                            currentGirlImageView.y = it.y.toFloat()
                            }
                            .start()
                        it.checkPoint();
                    }
                    else {

                    }
                }
            }
        }

        // subcribe observe for match object
        viewModel.matchObject.observe(this.viewLifecycleOwner) {
            matchObject ->
            with(binding) {
                if (!matchObject.isFinish()) {
                    matchImageView.setImageResource(matchObject.getCurrentImage())
                    matchImageView.visibility = View.VISIBLE
                    matchImageView.animate()
                        .x(matchObject.x.toFloat())
                        .y(matchObject.y.toFloat())
                        .setDuration(10)
                        .withEndAction {
                            //to make sure that it arrives,
                            //but not needed actually these two lines
//                        boyImageView.x = boyCharacter.x.toFloat()
//                        boyImageView.y = boyCharacter.y.toFloat()
                        }
                        .start()
                }
                else {
                    matchImageView.visibility = View.GONE
                }
            }
        }

        if (!viewModel.running) {
            viewModel.running = true
            viewModel.start()
        }


        binding.mainGameLayout.setOnTouchListener { view, motionEvent ->
//            Log.d("App", "Event Click on listener [x,y] = [${motionEvent.x},${motionEvent.y}]")

            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN ->
                    viewModel.changeDirectionOnCharacter(motionEvent.x.toInt(), motionEvent.y.toInt())
                else -> false
            }
        }

        // init variable model for data binding
        binding.model = viewModel
        binding.lifecycleOwner = this

        // init data

        viewModel.onMessageReveived(args.user)
        viewModel.initScore(args.newGame)

        return binding.root
    }

}