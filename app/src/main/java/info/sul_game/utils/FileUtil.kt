package info.sul_game.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import java.io.File
import java.io.IOException

object FileUtil {
    fun createImageFile(context: Context, extension: String = ".jpg"): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val validExtension = if (extension.lowercase() in MediaExtensions.IMAGE_EXTENSIONS) extension else ".jpg"
        return File.createTempFile(
            "IMG_${System.currentTimeMillis()}_", /* 파일 이름 */
            validExtension,
            storageDir
        )
    }

    fun createVideoFile(context: Context, extension: String = ".mp4"): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES)
        val validExtension = if (extension.lowercase() in MediaExtensions.VIDEO_EXTENSIONS) extension else ".mp4"
        return File.createTempFile(
            "VIDEO_${System.currentTimeMillis()}_", /* 파일 이름 */
            validExtension, /* 파일 확장자 */
            storageDir /* 파일 저장 위치 */
        )
    }

    fun createAudioFile(context: Context, extension: String = ".aac"): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val validExtension = if (extension.lowercase() in MediaExtensions.AUDIO_EXTENSIONS) extension else ".aac"
        return File.createTempFile(
            "AUDIO_${System.currentTimeMillis()}_", /* 파일 이름 */
            validExtension, /* 파일 확장자 */
            storageDir /* 파일 저장 위치 */
        )
    }

    fun createTempFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri) ?: throw IOException("Cannot open input stream from URI")
        val fileExtension = getFileExtension(context, uri)
        val file = when {
            fileExtension in MediaExtensions.VIDEO_EXTENSIONS -> createVideoFile(context, ".$fileExtension")
            fileExtension in MediaExtensions.AUDIO_EXTENSIONS -> createAudioFile(context, ".$fileExtension")
            fileExtension in MediaExtensions.IMAGE_EXTENSIONS -> createImageFile(context, ".$fileExtension")
            else -> throw IOException("Unsupported file type")
        }
        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            createTempFile(context, uri)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


 fun getFileExtension(context: Context, uri: Uri): String {
        val mimeType = context.contentResolver.getType(uri)
        return when {
            mimeType?.startsWith("video/") == true -> mimeType.substringAfterLast("/")
            mimeType?.startsWith("audio/") == true -> mimeType.substringAfterLast("/")
            mimeType?.startsWith("image/") == true -> mimeType.substringAfterLast("/")
            else -> ""
        }
    }

    // 추가: 파일 삭제 메서드
    fun deleteFile(file: File) {
        if (file.exists()) {
            file.delete()
        }
    }

    // 추가: 파일 이름 가져오기
    fun getFileName(context: Context, uri: Uri): String {
        var name: String = "temp_file"
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                name = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            }
        }
        return name
    }
}
