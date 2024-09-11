package info.sul_game.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File

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

    fun createAudioFile(context: Context, extension: String = ".mp3"): File {
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        val validExtension = if (extension.lowercase() in MediaExtensions.AUDIO_EXTENSIONS) extension else ".aac"
        return File.createTempFile(
            "AUDIO_${System.currentTimeMillis()}_", /* 파일 이름 */
            validExtension, /* 파일 확장자 */
            storageDir /* 파일 저장 위치 */
        )
    }

    fun createTempFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileExtension = getFileExtension(context, uri)
        val file = when {
            fileExtension in MediaExtensions.VIDEO_EXTENSIONS -> createVideoFile(context, ".${fileExtension}")
            fileExtension in MediaExtensions.AUDIO_EXTENSIONS -> createAudioFile(context, ".${fileExtension}")
            fileExtension in MediaExtensions.IMAGE_EXTENSIONS -> createImageFile(context, ".${fileExtension}")
            else -> createImageFile(context)  // 기본값으로 이미지 파일 생성
        }
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }

    private fun getFileExtension(context: Context, uri: Uri): String {
        val mimeType = context.contentResolver.getType(uri)
        return when {
            mimeType?.startsWith("video/") == true -> mimeType.substringAfterLast("/")
            mimeType?.startsWith("audio/") == true -> mimeType.substringAfterLast("/")
            mimeType?.startsWith("image/") == true -> mimeType.substringAfterLast("/")
            else -> ""
        }
    }
}
