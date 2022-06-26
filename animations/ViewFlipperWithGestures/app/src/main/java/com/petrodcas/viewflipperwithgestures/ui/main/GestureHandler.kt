package com.petrodcas.viewflipperwithgestures.ui.main

import android.content.Context
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs

/**
 * **Class to handle touch events.**
 *
 * It can detect simple movements only: To Left/Right/Top/Bottom and every diagonal.
 *
 * Callback functions can be set to specific properties so actions are performed when the desired
 * gestures are detected.
 *
 * These properties are: [onToRightDetected], [onToLeftDetected], [onToTopDetected], [onToBottomDetected],
 * [onToTopRightDetected], [onToTopLeftDetected], [onToBottomRightDetected] and [onToBottomLeftDetected].
 *
 * The threshold to be overcome before a gesture is considered as valid (a.k.a. [pagingMTouchSlop])
 * is different between diagonals and non-diagonals.
 *
 * Diagonal's threshold is based on [pagingMTouchSlop] and a given [diagonalMTouchSlopSensitivity],
 * which represents how less sensitive diagonals are when compared to non-diagonals movements.
 *
 */
class GestureHandler(context: Context) {
    private var lastX: Float = 0f
    private var lastY: Float = 0f

    /**
     * The threshold to be overcome before a gesture is considered as valid.
     *
     * By default, system's [ViewConfiguration.getScaledPagingTouchSlop] is used.
     *
     * For diagonals, this value is modified based on [diagonalMTouchSlopSensitivity].
     */
    var pagingMTouchSlop: Int = ViewConfiguration.get(context).scaledPagingTouchSlop
        set(value) {
            field = value
            diagonalMTouchSlop = diagonalMTouchSlopSensitivity * field
        }

    /**
     * How less sensitive gestures are to diagonals when compared to non-diagonals.
     *
     * A bigger value means that diagonal movements will be harder to detect. A value of 1 means the same
     * as for non-diagonals. In fact, its value can't be lower than 1.0.
     */
    var diagonalMTouchSlopSensitivity: Float = 3f
        set(value) {
            if (value < 1.0) {
                throw IllegalArgumentException("$value < 1.0. Field's value must be greater or equal than 1.0")
            }
            field = value
            diagonalMTouchSlop = field * pagingMTouchSlop
        }
    private var diagonalMTouchSlop: Float = pagingMTouchSlop * diagonalMTouchSlopSensitivity

    /**
     * Function to be called when a gesture is classified as a movement to the left.
     */
    var onToLeftDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to the right.
     */
    var onToRightDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to the top.
     */
    var onToTopDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to the bottom.
     */
    var onToBottomDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to top-left.
     */
    var onToTopLeftDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to top-right.
     */
    var onToTopRightDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to bottom-left.
     */
    var onToBottomLeftDetected: ((Unit) -> Unit)? = null
    /**
     * Function to be called when a gesture is classified as a movement to bottom-right.
     */
    var onToBottomRightDetected: ((Unit) -> Unit)? = null

    /**
     * Function to be called in order to handle touch events.
     *
     * This function should be called inside [android.view.View.setOnTouchListener] or alike.
     *
     * @param motionEvent The [MotionEvent] to be handled.
     * @return True if the [MotionEvent] was handled/consumed or false otherwise.
     */
    fun onGestureDetection(motionEvent: MotionEvent): Boolean {
        return when (motionEvent.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                lastX = motionEvent.x
                lastY = motionEvent.y
                true
            }
            MotionEvent.ACTION_UP -> {
                var consumed = true
                if (isAcceptedToTopLeft(motionEvent)) {
                    onToTopLeftDetected?.let { it(Unit) }
                }
                else if (isAcceptedToTopRight(motionEvent)) {
                    onToTopRightDetected?.let { it(Unit) }
                }
                else if (isAcceptedToBottomLeft(motionEvent)) {
                    onToBottomLeftDetected?.let { it(Unit) }
                }
                else if (isAcceptedToBottomRight(motionEvent)) {
                    onToBottomRightDetected?.let { it(Unit) }
                }
                else if (isAcceptedToLeft(motionEvent)) {
                    onToLeftDetected?.let { it(Unit) }
                } else if (isAcceptedToRight(motionEvent)) {
                    onToRightDetected?.let { it(Unit) }
                }
                else if (isAcceptedToBottom(motionEvent)) {
                    onToBottomDetected?.let { it(Unit) }
                }
                else if (isAcceptedToTop(motionEvent)) {
                    onToTopDetected?.let { it(Unit) }
                }
                else {
                    consumed = false
                }
                consumed
            }
            else -> false
        }
    }

    /**
     * Helper function to calculate the distance between two points.
     *
     * @param last the previous drawn point
     * @param current the current drawn point
     * @return The distance between the points as a positive number.
     */
    private fun distance(last: Float, current: Float) = abs(last - current)

    /**
     * Helper function to determine whether [distance] overcomes [pagingMTouchSlop] or [diagonalMTouchSlop]
     * (depending on [isDiagonal]) or not.
     *
     * @param distance The distance to be checked.
     * @param isDiagonal Whether [diagonalMTouchSlop] should be used instead of [pagingMTouchSlop] (true), or not (false).
     * @return true if distance is greater than the threshold or false otherwise.
     */
    private fun overcomesThreshold(distance: Float, isDiagonal: Boolean = false) : Boolean =
        if (!isDiagonal) distance > pagingMTouchSlop else distance > diagonalMTouchSlop


    // Helper functions to check the movement's direction
    private fun isToTop(motionEvent: MotionEvent): Boolean = lastY > motionEvent.y
    private fun isToLeft(motionEvent: MotionEvent): Boolean = lastX < motionEvent.x
    private fun isToBottom(motionEvent: MotionEvent): Boolean = lastY < motionEvent.y
    private fun isToRight(motionEvent: MotionEvent): Boolean = lastX > motionEvent.x

    // Helper functions to check if a movement is accepted (that is to say, that it overcomes the threshold)
    private fun isAcceptedToTop(motionEvent: MotionEvent): Boolean = isToTop(motionEvent) && overcomesThreshold(distance(lastY, motionEvent.y))
    private fun isAcceptedToLeft(motionEvent: MotionEvent): Boolean = isToLeft(motionEvent) && overcomesThreshold(distance(lastX, motionEvent.x))
    private fun isAcceptedToRight(motionEvent: MotionEvent): Boolean = isToRight(motionEvent) && overcomesThreshold(distance(lastX, motionEvent.x))
    private fun isAcceptedToBottom(motionEvent: MotionEvent): Boolean = isToBottom(motionEvent) && overcomesThreshold(distance(lastY, motionEvent.y))

    private fun isAcceptedToTopLeft(motionEvent: MotionEvent): Boolean = isToTop(motionEvent)
            &&  overcomesThreshold(distance(lastY, motionEvent.y), isDiagonal = true)
            && isToLeft(motionEvent)
            && overcomesThreshold(distance(lastX, motionEvent.x), isDiagonal = true)

    private fun isAcceptedToTopRight(motionEvent: MotionEvent): Boolean = isToTop(motionEvent)
            &&  overcomesThreshold(distance(lastY, motionEvent.y), isDiagonal = true)
            && isToRight(motionEvent)
            && overcomesThreshold(distance(lastX, motionEvent.x), isDiagonal = true)

    private fun isAcceptedToBottomLeft(motionEvent: MotionEvent): Boolean = isToBottom(motionEvent)
            &&  overcomesThreshold(distance(lastY, motionEvent.y), isDiagonal = true)
            && isToLeft(motionEvent)
            && overcomesThreshold(distance(lastX, motionEvent.x), isDiagonal = true)

    private fun isAcceptedToBottomRight(motionEvent: MotionEvent): Boolean = isToBottom(motionEvent)
            &&  overcomesThreshold(distance(lastY, motionEvent.y), isDiagonal = true)
            && isToRight(motionEvent)
            && overcomesThreshold(distance(lastX, motionEvent.x), isDiagonal = true)

}