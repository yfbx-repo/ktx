package com.yfbx.ktx

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * Author: Edward
 * Date: 2020-05-08
 * Description:
 */

/**
 * 图片压缩
 * @param size 单位 K
 */
suspend fun Bitmap.reSize(size: Long): Bitmap {
    val targetSize = size * 1024
    var currentSize = this.size
    if (currentSize <= targetSize) return this

    //先计算压缩比例，进行一次性压缩
    val scale = targetSize * 1f / currentSize
    var target = scale((width * scale).toInt(), (height * scale).toInt())
    currentSize = target.size
    if (currentSize <= targetSize) return target

    //再进行精确压缩
    return withContext(Dispatchers.IO) {
        while (target.size > targetSize) {
            target = target.scale((target.width * 0.9f).toInt(), (target.height * 0.9f).toInt())
        }
        target
    }
}


/**
 * 图片转 ByteArray
 */
fun Bitmap.getBytes(): ByteArray {
    val output = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, output)
    val byte = output.toByteArray()
    output.reset()
    output.close()
    return byte
}

/**
 * 保存图片到App私有目录，不需要权限，相册不可见
 */
fun Bitmap.save(file: File): Boolean {
    try {
        val fos = FileOutputStream(file)
        compress(Bitmap.CompressFormat.PNG, 100, fos)
        fos.flush()
        fos.close()
        return true
    } catch (t: Throwable) {
        t.printStackTrace()
    }
    return false
}


/**
 * 保存图片到相册，需要权限
 * [MediaStore.Images.Media.insertImage]
 */
fun Bitmap.save(): Boolean {
    val context = AppProvider.context
    val now = System.currentTimeMillis()
    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        ContentValues().apply {
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.DISPLAY_NAME, "$now")
            put(MediaStore.Images.Media.DATE_ADDED, now)
            put(MediaStore.Images.Media.DATE_MODIFIED, now)
        })
    if (uri != null) {
        val outputStream = context.contentResolver.openOutputStream(uri)
        val isSucceed = compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream?.flush()
        outputStream?.close()
        return isSucceed
    }
    return false
}

/**
 * Bitmap 转 Drawable
 */
fun Bitmap.drawable(): Drawable {
    return BitmapDrawable(AppProvider.context.resources, this)
}

/**
 * 圆形图片
 */
fun Bitmap.roundDrawable(): Drawable {
    val roundDrawable = RoundedBitmapDrawableFactory.create(AppProvider.context.resources, this)
    roundDrawable.isCircular = true
    return roundDrawable
}

/**
 * ImageView获取图片
 * (与View截图不同)
 */
fun ImageView.getBitmap(): Bitmap? {
    if (drawable == null) return null
    val width = drawable.intrinsicWidth
    val height = drawable.intrinsicHeight
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    drawable.draw(Canvas(bitmap))
    return bitmap
}

/**
 * View 截取
 */
fun View.capture(): Bitmap {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    bitmap.eraseColor(Color.TRANSPARENT)
    draw(Canvas(bitmap))
    return bitmap
}

val Bitmap.size: Int
    get() = getBytes().size
