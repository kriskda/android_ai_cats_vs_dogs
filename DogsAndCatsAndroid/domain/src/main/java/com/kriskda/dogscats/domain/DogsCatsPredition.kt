package com.kriskda.dogscats.domain

data class DogCatPrediction(
    val dogProbability: Float,
    val catProbability: Float,
)

fun FloatArray.toDogCatPrediction() =
    DogCatPrediction(
        catProbability = this[0],
        dogProbability = this[1],
    )