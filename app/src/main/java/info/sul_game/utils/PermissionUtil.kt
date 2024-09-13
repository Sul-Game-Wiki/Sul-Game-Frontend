package info.sul_game.utils

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


object PermissionUtil {




    const val REQUEST_CODE_STORAGE = 1003

    fun requestCameraPermission(fragment: Fragment,requestPermissionLauncher: ActivityResultLauncher<String>, onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 승인된 경우
            onPermissionGranted()
        } else {
            // 권한이 승인되지 않은 경우 요청
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    fun requestAudioPermission(fragment: Fragment, requestPermissionLauncher: ActivityResultLauncher<String>,onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 승인된 경우
            onPermissionGranted()
        } else {
            // 권한이 승인되지 않은 경우 요청
            requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    fun requestStoragePermission(
        fragment: Fragment,
        requestPermissionLauncher: ActivityResultLauncher<String>,
        onPermissionGranted: () -> Unit
    ) {
        val context = fragment.requireContext()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 (API 30) 이상
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 권한이 이미 승인된 경우
                onPermissionGranted()
            } else if(ContextCompat.checkSelfPermission(context, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 권한이 승인되지 않은 경우 요청
                    val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION).apply {
                        data = Uri.parse("package:${context.packageName}")
                    }
                    fragment.startActivityForResult(intent, REQUEST_CODE_STORAGE)

            }
        } else if(Build.VERSION.SDK_INT<Build.VERSION_CODES.R){
            // Android 10 이하
            val writePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val readPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
            if (writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED) {
                // 권한이 이미 승인된 경우
                onPermissionGranted()
            } else {
                // 권한 요청
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }
    }








}


