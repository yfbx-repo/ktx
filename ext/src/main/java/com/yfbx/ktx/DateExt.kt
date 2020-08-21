package com.yfbx.ktx

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Author: Edward
 * Date: 2020-06-17
 * Description:
 */

fun Date.format(format: String = "yyyy-MM-dd"): String = SimpleDateFormat(format, Locale.getDefault()).format(this)

fun Long.toDate(): Date = Date().apply { time = this@toDate }

fun Long.formatDate(format: String = "yyyy-MM-dd"): String = toDate().format(format)

/**
 * 日期字符串 转换成日期
 */
fun String.toDate(format: String = "yyyy-MM-dd"): Date? {
    try {
        return SimpleDateFormat(format, Locale.getDefault()).parse(this)
    } catch (e: ParseException) {
        //ignore
    }
    return null
}

fun Date.calendar(): Calendar = Calendar.getInstance().apply { time = this@calendar }

/**
 * 日期+day天 (几天后的日期)
 * eg. val sevenDaysLater:Date = Date()+7
 */
operator fun Date.plus(day: Int): Date {
    return Calendar.getInstance().apply {
        time = this@plus
        add(Calendar.DATE, day)
    }.time
}

/**
 * 日期-day天 (几天前的日期)
 * eg. val sevenDaysAgo:Date = Date()-7
 */
operator fun Date.minus(day: Int): Date {
    return Calendar.getInstance().apply {
        time = this@minus
        add(Calendar.DATE, -day)
    }.time
}


