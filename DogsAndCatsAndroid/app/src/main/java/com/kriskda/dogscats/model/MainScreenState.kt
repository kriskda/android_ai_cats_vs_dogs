package com.kriskda.dogscats.model

import com.kriskda.dogscats.domain.DogCatPrediction

sealed interface MainScreenState {
    object Intro : MainScreenState
    object Predicting : MainScreenState
    data class PredictionResult(val data: Prediction) : MainScreenState
    object Error : MainScreenState
}

data class Prediction(
    val dogProbability: Float,
    val catProbability: Float
)

fun DogCatPrediction.toPrediction() =
    Prediction(
        dogProbability = dogProbability,
        catProbability = catProbability
    )

fun Float.toPercentage() = "%.1f".format(this * 100)
