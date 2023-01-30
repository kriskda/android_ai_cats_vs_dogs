package com.kriskda.dogscats

import android.Manifest.permission.CAMERA
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenActivity : ComponentActivity() {

    private val cameraProviderResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                // cut and paste the previous startCamera() call here.
                //startCamera()
            } else {
                Toast.makeText(this, "The camera permission is required", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (hasCameraPermission().not()) {
            cameraProviderResult.launch(CAMERA)
        }

        setContent {
            MainScreen()
        }
    }

    private fun hasCameraPermission() =
        ContextCompat.checkSelfPermission(this, CAMERA) == PackageManager.PERMISSION_GRANTED
}
