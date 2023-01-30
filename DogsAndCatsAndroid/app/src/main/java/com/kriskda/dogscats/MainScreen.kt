package com.kriskda.dogscats

import android.graphics.Bitmap
import androidx.camera.core.CameraSelector
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kriskda.dogscats.model.MainScreenState
import com.kriskda.dogscats.model.Prediction
import com.kriskda.dogscats.model.toPercentage

@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    val uiState = viewModel.uiState
    MainScreenContent(
        uiState,
        { bitmap -> viewModel.onCalculatePrediction(bitmap) },
        { viewModel.onClear() }
    )
}

@Composable
fun MainScreenContent(
    uiState: MainScreenState,
    onPredictClick: (bitmap: Bitmap) -> Unit,
    onClearClick: () -> Unit,
) {
    val isPredictButtonVisible = uiState == MainScreenState.Intro

    Box(modifier = Modifier.fillMaxSize()) {
        CameraPreview(onPredictClick, isPredictButtonVisible)
        if (uiState is MainScreenState.PredictionResult) {
            PredictionResult(uiState.data)
        }
        if (isPredictButtonVisible.not()) {
            ActionButton(
                name = stringResource(R.string.button_clear),
                onClick = onClearClick
            )
        }
    }
}

@Composable
fun BoxScope.ActionButton(
    name: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .testTag("action_button")
            .align(Alignment.BottomCenter)
            .padding(16.dp),
        shape = RoundedCornerShape(100.dp)
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = name,
            fontSize = 26.sp,
        )
    }
}

@Composable
fun BoxScope.PredictionResult(prediction: Prediction) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .padding(8.dp)
            .align(Alignment.TopCenter)
    ) {
        PredictionElement(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.name_dog),
            prediction = prediction.dogProbability
        )
        PredictionElement(
            modifier = Modifier.weight(1f),
            label = stringResource(id = R.string.name_cat),
            prediction = prediction.catProbability
        )
    }
}

@Composable
private fun PredictionElement(
    modifier: Modifier,
    label: String,
    prediction: Float
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = label,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(id = R.string.percentage, prediction.toPercentage())
            )
        }
        LinearProgressIndicator(
            progress = prediction,
            modifier = Modifier
                .padding(4.dp)
                .height(16.dp),
            backgroundColor = Color.LightGray,
            color = Color.Green
        )
    }
}

@Composable
fun CameraPreview(
    onGetBitmap: (Bitmap) -> Unit,
    isPredictButtonVisible: Boolean
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val previewView = remember { PreviewView(context) }

    val preview = androidx.camera.core.Preview.Builder().build()
        .also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            factory = { ctx ->
                val executor = ContextCompat.getMainExecutor(ctx)
                cameraProviderFuture.addListener({
                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    with(cameraProviderFuture.get()) {
                        unbindAll()
                        bindToLifecycle(lifecycleOwner, cameraSelector, preview)
                    }
                }, executor)
                previewView
            },
            modifier = Modifier.fillMaxSize(),
        )

        if (isPredictButtonVisible) {
            ActionButton(
                name = stringResource(R.string.button_recognize),
                onClick = { previewView.bitmap?.let { onGetBitmap(it) } },
            )
        }
    }
}

@Preview
@Composable
private fun PredictionResultPreview() {
    val data = Prediction(
        0.7f,
        0.3f,
    )
    Box {
        PredictionResult(data)
    }
}
