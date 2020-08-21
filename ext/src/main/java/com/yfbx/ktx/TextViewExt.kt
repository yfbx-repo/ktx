package com.yfbx.ktx

import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Author: Edward
 * Date: 2020-06-17
 * Description:
 */

/**
 *  R.color.xxx
 */
fun TextView.setTxtColor(@ColorRes id: Int, alpha: Float) {
    setTextColor(ContextCompat.getColor(context, id).withAlpha(alpha))
}

fun TextView.setDrawableStart(@DrawableRes id: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
}

fun TextView.setDrawableTop(@DrawableRes id: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, id, 0, 0)
}

fun TextView.setDrawableEnd(@DrawableRes id: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, id, 0)
}

fun TextView.setDrawableBottom(@DrawableRes id: Int) {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, id)
}

fun TextView.setDrawableNull() {
    this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
}


fun TextView.setSpan(text: String, block: SpannableString.() -> Unit) {
    val span = SpannableString(text)
    span.block()
    setText(span)
}


//----------------------------EditText-------------------------------------------------------------

var EditText.txt
    get() = text?.toString()
    set(value) = setText(value)


fun EditText.afterTextChanged(afterTextChanged: (Editable?) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }
    })
}