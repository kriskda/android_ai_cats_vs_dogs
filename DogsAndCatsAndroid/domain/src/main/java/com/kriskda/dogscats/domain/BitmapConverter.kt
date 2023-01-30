package com.kriskda.dogscats.domain

import android.graphics.Bitmap
import android.graphics.Color
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.inject.Inject

class BitmapConverter @Inject constructor() {

    fun scaleAndNormalizeToByteBuffer(bitmap: Bitmap) =
        bitmap.scale(INPUT_WIDTH, INPUT_HEIGHT).toNormalizedByteBuffer()

    private fun Bitmap.scale(width: Int, height: Int) =
        Bitmap.createScaledBitmap(this, width, height, true)

    private fun Bitmap.toNormalizedByteBuffer(): ByteBuffer {
        val imageData = ByteBuffer.allocateDirect(
            java.lang.Float.BYTES * RGB_CHANNELS * width * height
        )
        imageData.order(ByteOrder.nativeOrder())

        val pixels = IntArray(width * height)
        getPixels(pixels, 0, width, 0, 0, width, height)
        for (pixel in pixels) {
            imageData.putFloat(Color.red(pixel) / PIXEL_NORMALIZATION)
            imageData.putFloat(Color.green(pixel) / PIXEL_NORMALIZATION)
            imageData.putFloat(Color.blue(pixel) / PIXEL_NORMALIZATION)
        }
        return imageData
    }

    companion object {
        private const val INPUT_WIDTH = 150
        private const val INPUT_HEIGHT = 150
        private const val RGB_CHANNELS = 3
        private const val PIXEL_NORMALIZATION = 255.0f
    }
}