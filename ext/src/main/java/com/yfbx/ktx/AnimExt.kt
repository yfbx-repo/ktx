package com.yfbx.ktx

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator

/**
 * @Author: Edward
 * @Date: 2019/06/25
 * @Description:
 */

/**
 * 旋转动画
 * @param duration 旋转一周所用时间(控制旋转速度)
 */
fun View.rotateRepeat(duration: Long = 1000): ObjectAnimator {
    val rotation = ObjectAnimator.ofFloat(this, "rotation", 0f, 359f)
    rotation.repeatCount = ObjectAnimator.INFINITE
    rotation.interpolator = LinearInterpolator()
    rotation.duration = duration
    rotation.start()
    return rotation
}

/**
 * 值变化
 */
fun animValue(start: Int, end: Int, onUpdate: (Int) -> Unit) {
    val anim = ValueAnimator.ofInt(start, end)
    anim.duration = 300
    anim.addUpdateListener {
        val value = it.animatedValue as Int
        onUpdate.invoke(value)
    }
    anim.start()
}

/**
 * 旋转动画
 */
fun View.rotate(startAngle: Float, endAngle: Float, duration: Long = 300) {
    ObjectAnimator.ofFloat(this, "rotation", startAngle, endAngle)
        .setDuration(duration)
        .start()
}

/**
 * Y轴平移
 */
fun View.translateY(start: Float, end: Float, duration: Long = 300) {
    ObjectAnimator.ofFloat(this, "translationY", start, end)
        .setDuration(duration)
        .start()
}


/**
 * 设置缩放
 */
fun View.scaleTo(scale: Float) {
    scaleX = scale
    scaleY = scale
}

/**
 * 缩放动画点击事件
 */
fun View.onAnimClick(onClick: () -> Unit) {
    setOnClickListener {
        startAnimation(AnimationUtils.loadAnimation(context, R.anim.scale_click))
        onClick.invoke()
    }
}

/**
 * 带缩放动画的点击事件
 */
fun View.setOnScaleClick(onClick: () -> Unit) {
    setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> scaleTo(0.95f)
            MotionEvent.ACTION_UP -> {
                scaleTo(1.0f)
                onClick.invoke()
            }
            MotionEvent.ACTION_CANCEL -> scaleTo(1.0f)
        }
        performClick()
    }
}

