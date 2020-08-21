package com.yfbx.ktx

import android.content.Context
import android.graphics.Typeface
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes


/**
 * Author: Edward
 * Date: 2019-09-04
 * Description:
 */

@Suppress("FunctionName")
fun RichText(vararg src: CharSequence) = SpannableStringBuilder().apply {
    src.forEach { append(it) }
}

fun CharSequence.colour(@ColorInt color: Int) = SpannableString(this).apply {
    setSpan(ForegroundColorSpan(color), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun CharSequence.color(@ColorRes color: Int) = SpannableString(this).apply {
    setSpan(ForegroundColorSpan(findColor(color)), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun CharSequence.size(size: Int) = SpannableString(this).apply {
    setSpan(AbsoluteSizeSpan(size), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun CharSequence.typeface(typeface: Int) = SpannableString(this).apply {
    setSpan(StyleSpan(typeface), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun CharSequence.bold() = this.typeface(Typeface.BOLD)
fun CharSequence.italic() = this.typeface(Typeface.ITALIC)

fun drawableSpan(context: Context, @DrawableRes drawable: Int) = SpannableString("").apply {
    //字符串会被覆盖，所以字符串内容是什么并不重要
    setSpan(ImageSpan(context, drawable), 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
}

fun CharSequence.click(@ColorInt color: Int, underline: Boolean = true, onClick: () -> Unit) =
    SpannableString(this).apply {
        setSpan(object : ClickableSpan() {
            override fun onClick(widget: View) {
                onClick.invoke()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.color = color
                ds.isUnderlineText = underline
            }
        }, 0, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

fun CharSequence.click(
    textView: TextView,
    @ColorRes color: Int,
    underline: Boolean = true,
    onClick: () -> Unit
): SpannableString {
    textView.movementMethod = LinkMovementMethod.getInstance()
    return this.click(findColor(color), underline, onClick)
}
