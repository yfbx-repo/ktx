package com.yfbx.ktx

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

/**
 * Author: Edward
 * Date: 2020-06-17
 * Description:
 */

fun findColor(@ColorRes id: Int): Int = ContextCompat.getColor(AppProvider.context, id)

/**
 * @param alpha 0.0-1.0
 */
fun Int.withAlpha(alpha: Float): Int {
    require(alpha in 0f..1.0f) { "alpha must be between 0 and 1." }
    val a = (alpha * 255).roundToInt()
    return (this and 0x00ffffff) or (a shl 24)
}
