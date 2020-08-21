package com.yfbx.ktx

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * Author: Edward
 * Date: 2020-06-17
 * Description:
 */

fun View.setVisible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.setPaddingStart(paddingStart: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

fun View.setPaddingTop(paddingTop: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

fun View.setPaddingEnd(paddingEnd: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

fun View.setPaddingBottom(paddingBottom: Int) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/**
 * @param id Color resource
 * @param alpha 0.0-1.0
 */
fun View.setBackColor(@ColorRes id: Int, alpha: Float = 1.0f) {
    val color = ContextCompat.getColor(context, id).withAlpha(alpha)
    setBackgroundColor(color)
}

/**
 * Bold
 */
fun TextView.setBold(bold: Boolean) {
    paint.isFakeBoldText = bold
}


/**
 * 设置圆角
 */
fun View.setCorner(corner: Float) {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(0, 0, view.measuredWidth, view.measuredHeight, corner.dp)
        }
    }
}

/**
 * 剪裁成圆形
 */
fun View.setRound() {
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(0, 0, view.measuredWidth, view.measuredHeight)
        }
    }
}


fun RecyclerView.smoothScroll(
    position: Int,
    snapPreference: Int = LinearSmoothScroller.SNAP_TO_START
) {
    layoutManager?.startSmoothScroll(object : LinearSmoothScroller(context) {
        override fun getVerticalSnapPreference(): Int {
            return snapPreference
        }
    }.apply {
        targetPosition = position
    })
}
