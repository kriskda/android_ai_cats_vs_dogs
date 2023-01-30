package com.kriskda.dogscats

import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kriskda.dogscats.domain.GetDogsCatsPrediction
import com.kriskda.dogscats.model.MainScreenState
import com.kriskda.dogscats.model.toPrediction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getDogsCatsPrediction: GetDogsCatsPrediction
) : ViewModel() {

    var uiState: MainScreenState by mutableStateOf(MainScreenState.Intro)
        private set

    fun onCalculatePrediction(bitmap: Bitmap) {
        viewModelScope.launch {
            runCatching {
                uiState = MainScreenState.Predicting
                getDogsCatsPrediction(bitmap)
            }.onSuccess {
                uiState = MainScreenState.PredictionResult(it.toPrediction())
            }.onFailure {
                uiState = MainScreenState.Error
            }
        }
    }

    fun onClear() {
        uiState = MainScreenState.Intro
    }
}
