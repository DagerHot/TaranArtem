package com.example.cameracall

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import com.example.cameracall.ui.theme.CameraCallTheme

class MainActivity : ComponentActivity() {

    private val requestCameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCamera() // Якщо дозвіл надано, відкриваємо камеру
        } else {
            Toast.makeText(this, "Дозвіл на камеру відхилено", Toast.LENGTH_SHORT).show()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val imageBitmap = result.data?.extras?.get("data") as Bitmap?
            capturedImage = imageBitmap // Зберігаємо отримане зображення
        }
    }

    private var capturedImage by mutableStateOf<Bitmap?>(null)

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent) // Викликаємо запуск камери
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Камеру не знайдено", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CameraCallTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    val configuration = LocalConfiguration.current
                    val screenHeight = configuration.screenHeightDp.dp
                    val screenWidth = configuration.screenWidthDp.dp

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        // Відображення зображення, якщо воно є
                        capturedImage?.let { bitmap ->
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(top = screenHeight * 0.2f)
                                    .size(screenWidth * 0.8f)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            when {
                                // Перевірка дозволу на камеру
                                android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M -> {
                                    when {
                                        checkSelfPermission(android.Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                                            openCamera() // Дозвіл вже надано
                                        }
                                        else -> {
                                            requestCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA) // Запит на дозвіл
                                        }
                                    }
                                }
                                else -> {
                                    openCamera() // Для старіших версій Android
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = screenHeight * 0.05f)
                    ) {
                        Text(text = "Зробити фото")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    CameraCallTheme {
        // Попередній перегляд UI
    }
}
