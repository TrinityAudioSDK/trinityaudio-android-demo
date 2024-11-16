package ai.trinityaudio.sdk.sample

import android.animation.ValueAnimator
import android.util.TypedValue
import android.view.View

fun View.animateHeightChange(
    endHeightDp: Int,
    duration: Long = 300,
) {
    // Convert dp to pixels
    val displayMetrics = context.resources.displayMetrics
    val endHeightPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, endHeightDp.toFloat(), displayMetrics).toInt()

    // Start height from the current height of the view
    val startHeight = this.height
    ValueAnimator.ofInt(startHeight, endHeightPx).apply {
        this.duration = duration
        addUpdateListener { animator ->
            val newHeight = animator.animatedValue as Int
            layoutParams =
                layoutParams.apply {
                    height = newHeight
                }
        }
        start()
    }
}
