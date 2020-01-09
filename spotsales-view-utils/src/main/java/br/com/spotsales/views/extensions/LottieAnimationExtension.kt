package br.com.spotsales.views.extensions

import android.animation.ValueAnimator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView.loadAnimation(file: String, loop: Boolean = true) {
    repeatCount =
            if (loop) {
                ValueAnimator.INFINITE
            } else {
                0
            }

    cancelAnimation()
    setAnimation(file)
    playAnimation()
}