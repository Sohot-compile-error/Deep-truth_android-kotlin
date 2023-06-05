package kau.sohothackathon.compileerror.data

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kau.sohothackathon.compileerror.domain.FileRepository
import kau.sohothackathon.compileerror.domain.model.MediaFile
import kau.sohothackathon.compileerror.domain.model.MediaType
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : FileRepository {

    override fun fetchAllMediaFiles(): List<MediaFile> {
        val mediaFiles = mutableListOf<MediaFile>()
        val contentResolver: ContentResolver = context.contentResolver

        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DATA
        )
        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"
        val queryUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val selection = "${MediaStore.Audio.Media.MIME_TYPE} = 'audio/mpeg'"

        val cursor = contentResolver.query(
            queryUri,
            projection,
            selection,
            null,
            sortOrder
        )

        cursor?.use { cur ->
            val idColumn = cur.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val dataColumn = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cur.moveToNext()) {
                val id = cur.getLong(idColumn)
                val name = cur.getString(nameColumn)
                val data = cur.getString(dataColumn)
                val contentUri = ContentUris.withAppendedId(queryUri, id)

                mediaFiles.add(MediaFile(name, data, contentUri, MediaType.AUDIO))
            }
        }

        // 비디오 파일 쿼리
        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DATA
        )
        val videoSortOrder = "${MediaStore.Video.Media.DISPLAY_NAME} ASC"
        val videoQueryUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val videoCursor = contentResolver.query(
            videoQueryUri,
            videoProjection,
            null,
            null,
            videoSortOrder
        )

        videoCursor?.use { cur ->
            val videoIdColumn = cur.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val videoNameColumn = cur.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val videoDataColumn = cur.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

            while (cur.moveToNext()) {
                val videoId = cur.getLong(videoIdColumn)
                val videoName = cur.getString(videoNameColumn)
                val videoData = cur.getString(videoDataColumn)
                val videoContentUri = ContentUris.withAppendedId(videoQueryUri, videoId)

                mediaFiles.add(MediaFile(videoName, videoData, videoContentUri, MediaType.VIDEO))
            }
        }

        return mediaFiles
    }
}