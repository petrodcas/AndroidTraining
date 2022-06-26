package com.petrodcas.viewflipperwithgestures.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.annotation.AnimRes
import com.petrodcas.viewflipperwithgestures.R
import com.petrodcas.viewflipperwithgestures.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Prepares the gestureHandler with callbacks for each direction
        val gestureHandler = GestureHandler(requireContext())
        gestureHandler.onToRightDetected = getAnimCallback(R.anim.push_left_in, R.anim.push_left_out, true)
        gestureHandler.onToLeftDetected = getAnimCallback(R.anim.push_right_in, R.anim.push_right_out, false)
        gestureHandler.onToBottomDetected = getAnimCallback(R.anim.push_top_in, R.anim.push_top_out, true)
        gestureHandler.onToTopDetected = getAnimCallback(R.anim.push_bottom_in, R.anim.push_bottom_out, false)
        gestureHandler.onToBottomRightDetected = getAnimCallback(R.anim.push_topleft_in, R.anim.push_topleft_out, true)
        gestureHandler.onToBottomLeftDetected = getAnimCallback(R.anim.push_topright_in, R.anim.push_topright_out, false)
        gestureHandler.onToTopLeftDetected = getAnimCallback(R.anim.push_bottomright_in, R.anim.push_bottomright_out, false)
        gestureHandler.onToTopRightDetected = getAnimCallback(R.anim.push_bottomleft_in, R.anim.push_bottomleft_out, true)
        // Sets the gestureHandler to manage the motionEvent received by the ViewFlipper
        binding.viewFlipper.setOnTouchListener { _, motionEvent ->
            gestureHandler.onGestureDetection(motionEvent)
        }
    }

    /**
     * Creates lambda functions that sets specific in/out animations to the ViewFlipper before
     * showing the next or previous element depending on [isShowNext]'s value.
     *
     * @param inAnim The animation to be played when the element is entering
     * @param outAnim The animation to be played when the element is exiting
     * @param isShowNext Whether the element to be shown is the next (true) or the previous (false).
     * @return The generated lambda function.
     */
    private fun getAnimCallback(
        @AnimRes inAnim: Int,
        @AnimRes outAnim: Int,
        isShowNext: Boolean
    ): (Unit) -> Unit = {
        binding.viewFlipper.setInAnimation(requireContext(), inAnim)
        binding.viewFlipper.setOutAnimation(requireContext(), outAnim)
        if (isShowNext) binding.viewFlipper.showNext() else binding.viewFlipper.showPrevious()
    }

}