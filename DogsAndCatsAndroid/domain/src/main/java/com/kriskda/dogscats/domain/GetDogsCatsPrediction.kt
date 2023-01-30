package com.kriskda.dogscats.domain

import android.graphics.Bitmap
import javax.inject.Inject

class GetDogsCatsPrediction @Inject constructor(
    private val bitmapConverter: BitmapConverter,
    private val dogsCatsDetector: DogsCatsDetector
) {

    suspend operator fun invoke(bitmap: Bitmap): DogCatPrediction {
        val byteBuffer = bitmapConverter.scaleAndNormalizeToByteBuffer(bitmap)
        return dogsCatsDetector.runDetection(byteBuffer).toDogCatPrediction()
    }
}
