package info.sul_game.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


object PermissionUtil {

    const val REQUEST_CODE_CAMERA = 1001
    const val REQUEST_CODE_AUDIO = 1002
    const val REQUEST_CODE_STORAGE = 1003

    fun requestCameraPermission(fragment: Fragment, onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 승인된 경우
            onPermissionGranted()
        } else {
            // 권한이 승인되지 않은 경우 요청
            fragment.requestPermissions(arrayOf(Manifest.permission.CAMERA), REQUEST_CODE_CAMERA)
        }
    }

    fun requestAudioPermission(fragment: Fragment, onPermissionGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(fragment.requireContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 승인된 경우
            onPermissionGranted()
        } else {
            // 권한이 승인되지 않은 경우 요청
            fragment.requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_CODE_AUDIO)
        }
    }

    fun requestStoragePermission(fragment: Fragment, onPermissionGranted: () -> Unit) {
        val context = fragment.requireContext()
        val writePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        Log.d("PermissionUtil", "Write Permission: $writePermission, Read Permission: $readPermission")
        Log.d("PermissionUtil", "Context: ${fragment.requireContext()}")
        if (writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED) {
            // 권한이 이미 승인된 경우
            onPermissionGranted()
        } else {
            // 권한이 승인되지 않은 경우 요청
            Log.d("PermissionUtil", "Requesting storage permissions...")
            fragment.requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_STORAGE
            )

        }
    }






    fun handlePermissionsResult(
        fragment: Fragment,
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        onPermissionGranted: () -> Unit
    ) {
        when (requestCode) {
            REQUEST_CODE_CAMERA -> {
                handleSinglePermissionResult(fragment, permissions, grantResults, Manifest.permission.CAMERA, "카메라", onPermissionGranted)
            }
            REQUEST_CODE_AUDIO -> {
                handleSinglePermissionResult(fragment, permissions, grantResults, Manifest.permission.RECORD_AUDIO, "오디오", onPermissionGranted)
            }
            REQUEST_CODE_STORAGE -> {
                Log.d("PermissionUtil", "Request Code: $requestCode")
                Log.d("PermissionUtil", "Permissions: ${permissions.joinToString()}")
                Log.d("PermissionUtil", "Grant Results: ${grantResults.joinToString()}")
                val writePermissionIndex = permissions.indexOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                val readPermissionIndex = permissions.indexOf(Manifest.permission.READ_EXTERNAL_STORAGE)

                val writePermissionGranted = writePermissionIndex >= 0 && grantResults.isNotEmpty() && grantResults[writePermissionIndex] == PackageManager.PERMISSION_GRANTED
                val readPermissionGranted = readPermissionIndex >= 0 && grantResults.isNotEmpty() && grantResults[readPermissionIndex] == PackageManager.PERMISSION_GRANTED

                if (writePermissionGranted && readPermissionGranted) {
                    Toast.makeText(fragment.requireContext(), "저장소 권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
                    onPermissionGranted()
                } else {
                    Toast.makeText(fragment.requireContext(), "저장소 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleSinglePermissionResult(
        fragment: Fragment,
        permissions: Array<out String>,
        grantResults: IntArray,
        permission: String,
        permissionName: String,
        onPermissionGranted: () -> Unit
    ) {
        val index = permissions.indexOf(permission)
        Log.d("PermissionUtil", "Handling permission result for: $permissionName")
        Log.d("PermissionUtil", "Permissions: ${permissions.joinToString()}")
        Log.d("PermissionUtil", "Grant Results: ${grantResults.joinToString()}")
        Log.d("PermissionUtil", "Index: $index")
        if (index >= 0 && grantResults.isNotEmpty() && grantResults[index] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(fragment.requireContext(), "$permissionName 권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
            Log.d("PermissionUtil", "$permissionName 권한이 승인되었습니다.")
            onPermissionGranted()
        } else {
            Toast.makeText(fragment.requireContext(), "$permissionName 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            Log.d("PermissionUtil", "$permissionName 권한이 필요합니다.")
        }
    }}


