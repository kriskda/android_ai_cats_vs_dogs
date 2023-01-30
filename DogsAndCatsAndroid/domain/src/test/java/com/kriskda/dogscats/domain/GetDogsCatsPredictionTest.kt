package com.kriskda.dogscats.domain

import android.graphics.Bitmap
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.kotlin.*
import java.nio.ByteBuffer

class GetDogsCatsPredictionTest {

    private val mockBitmap: Bitmap = mock()
    private val byteBuffer = ByteBuffer.allocate(1)
    private val mockBitmapConverter: BitmapConverter = mock {
        on { scaleAndNormalizeToByteBuffer(any()) } doReturn byteBuffer
    }
    private val mockDogsCatsDetector: DogsCatsDetector = mock {
        on { runDetection(any()) } doReturn floatArrayOf(0.3f, 0.7f)
    }
    private val systemUnderTest = GetDogsCatsPrediction(
        mockBitmapConverter,
        mockDogsCatsDetector
    )

    @Test
    fun `gets dogs cats prediction`() = runTest {
        val dogCatPrediction = systemUnderTest.invoke(mockBitmap)

        verify(mockBitmapConverter).scaleAndNormalizeToByteBuffer(eq(mockBitmap))
        verify(mockDogsCatsDetector).runDetection(eq(byteBuffer))
        assertThat(dogCatPrediction.dogProbability).isEqualTo(0.3f)
        assertThat(dogCatPrediction.catProbability).isEqualTo(0.7f)
    }
}