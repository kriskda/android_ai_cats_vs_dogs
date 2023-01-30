package com.kriskda.dogscats.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class DogsCatsPredictionKtTest {

    @Test
    fun `converts float array to DogCatPrediction`() {
        val dogProbability = 0.3f
        val catProbability = 0.7f
        val dogCatPrediction = floatArrayOf(dogProbability, catProbability).toDogCatPrediction()

        assertThat(dogCatPrediction.dogProbability).isEqualTo(dogProbability)
        assertThat(dogCatPrediction.catProbability).isEqualTo(catProbability)
    }
}