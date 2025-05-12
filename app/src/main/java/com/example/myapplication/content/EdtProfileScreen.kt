package com.example.myapplication.content

import android.Manifest
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.model.Profile
import com.example.myapplication.utilities.Notification
import com.example.myapplication.view.ProfileViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navigation: NavHostController) {
    val viewModel = koinViewModel<ProfileViewModel> { parametersOf(navigation) }
    val state = viewModel.viewState

    val context = LocalContext.current

    val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    var hasStoragePermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                storagePermission
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var timeError by remember { mutableStateOf<String?>(null) }

    val storagePermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasStoragePermission = isGranted
            if (!isGranted) {
                navigation.popBackStack()
            }
        }

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasCameraPermission = isGranted
        }

    val notificationPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (!isGranted) {
                timeError = "Для работы уведомлений требуется разрешение"
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }
        }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        LaunchedEffect(Unit) {
            if (!Notification.areNotificationsEnabled(context)) {
                timeError = "Уведомления отключены в настройках системы"
                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                }
                context.startActivity(intent)
            } else if (!Notification.hasNotificationPermission(context)) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    if (!hasStoragePermission) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Доступ к файловому хранилищу") },
            text = { Text(text = "Для редактирования профиля требуется доступ к файловому хранилищу. Разрешить?") },
            confirmButton = {
                TextButton(onClick = {
                    storagePermissionLauncher.launch(storagePermission)
                }) {
                    Text("Разрешить")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    navigation.popBackStack()
                }) {
                    Text("Отмена")
                }
            }
        )
        return
    }

    if (!hasCameraPermission) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text(text = "Доступ к камере") },
            text = { Text(text = "Для использования камеры требуется разрешение. Разрешить?") },
            confirmButton = {
                TextButton(onClick = {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }) {
                    Text("Разрешить")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    navigation.popBackStack()
                }) {
                    Text("Отмена")
                }
            }
        )
        return
    }
    var favouriteLessonTime by remember { mutableStateOf(state.favouriteLessonTime) }

    var showImageSourceDialog by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            try {
                context.contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                val inputStream = context.contentResolver.openInputStream(it)
                val file = File(context.cacheDir, "avatar_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
                state.avatarUri = file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempImageUri != null) {
            try {
                val inputStream = context.contentResolver.openInputStream(tempImageUri!!)
                val file = File(context.cacheDir, "avatar_${System.currentTimeMillis()}.jpg")
                FileOutputStream(file).use { outputStream ->
                    inputStream?.copyTo(outputStream)
                }
                state.avatarUri = file.absolutePath
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text(text = "Выбор источника") },
            text = { Text(text = "Выберите источник для аватарки") },
            confirmButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    galleryLauncher.launch(arrayOf("image/*"))
                }) {
                    Text("Галерея")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    val file = File(context.cacheDir, "temp_camera_${System.currentTimeMillis()}.jpg")
                    tempImageUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        file
                    )
                    cameraLauncher.launch(tempImageUri!!)
                }) {
                    Text("Камера")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Редактирование профиля") },
                navigationIcon = {
                    IconButton(onClick = { navigation.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    Button(
                        onClick = {
                            if (isValidTime(favouriteLessonTime)){
                                state.favouriteLessonTime = favouriteLessonTime
                                viewModel.onSaveProfile(
                                    Profile(
                                        fullName = state.fullName,
                                        position = state.position,
                                        email = state.email,
                                        avatarUri = state.avatarUri,
                                        resumeUrl = state.resumeUrl,
                                        favouriteLessonTime = state.favouriteLessonTime
                                    )
                                )
                                navigation.navigate("profile")
                            }
                            else {
                                timeError = "Введите время в формате HH:mm"
                            }
                        }
                    ) {
                        Text("Сохранить")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                state.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                AsyncImage(
                    model = state.avatarUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clickable { showImageSourceDialog = true }
                        .clip(shape = CircleShape),
                    error = painterResource(R.drawable.error)

                )

                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = { state.fullName = it },
                    label = { Text("ФИО") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = state.position,
                    onValueChange = { state.position = it },
                    label = { Text("Должность") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { state.email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                OutlinedTextField(
                    value = state.resumeUrl,
                    onValueChange = { state.resumeUrl = it },
                    label = { Text("URL резюме") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = favouriteLessonTime,
                        onValueChange = {
                            favouriteLessonTime = it
                            timeError = if (isValidTime(it)) null else "Введите время в формате HH:mm"
                        },
                        label = { Text("Время любимой пары") },
                        modifier = Modifier.weight(1f),
                        isError = timeError != null,
                        supportingText = {
                            timeError?.let { Text(it) }
                        }
                    )
                    IconButton(
                        onClick = {
                            val calendar = Calendar.getInstance()
                            TimePickerDialog(
                                context,
                                { _, hour, minute ->
                                    favouriteLessonTime = String.format("%02d:%02d", hour, minute)
                                    timeError = null
                                },
                                calendar.get(Calendar.HOUR_OF_DAY),
                                calendar.get(Calendar.MINUTE),
                                true
                            ).show()
                        }
                    ) {
                        Icon(
                            Icons.Default.DateRange,
                            contentDescription = "Выбрать время",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }
}

private fun isValidTime(time: String): Boolean {
    if (time.isEmpty()) return false
    return try {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        format.isLenient = false
        format.parse(time)
        true
    } catch (e: Exception) {
        false
    }
}

