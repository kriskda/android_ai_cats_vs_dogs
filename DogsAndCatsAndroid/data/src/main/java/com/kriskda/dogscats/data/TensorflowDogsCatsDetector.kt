package com.kriskda.dogscats.data

import android.content.Context
import androidx.core.view.NestedScrollingChild3
import com.kriskda.dogscats.data.ml.Dogscatsmodel
import com.kriskda.dogscats.domain.DogsCatsDetector
import dagger.hilt.android.qualifiers.ApplicationContext
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import javax.inject.Inject

class TensorflowDogsCatsDetector @Inject constructor(
    @ApplicationContext private val context: Context
) : DogsCatsDetector {

    override suspend fun runDetection(byteBuffer: ByteBuffer): FloatArray {
        val inputFeature = TensorBuffer.createFixedSize(DATA_SHAPE, DataType.FLOAT32)
            .apply {
                loadBuffer(byteBuffer)
            }

        val model = Dogscatsmodel.newInstance(context)
        val outputs = model.process(inputFeature)
        model.close()

        return outputs.outputFeature0AsTensorBuffer.floatArray
    }

    companion object {
        private const val INPUT_WIDTH = 150
        private const val INPUT_HEIGHT = 150
        private const val RGB_CHANNELS = 3
        private val DATA_SHAPE = intArrayOf(1, INPUT_WIDTH, INPUT_HEIGHT, RGB_CHANNELS)
    }
}
