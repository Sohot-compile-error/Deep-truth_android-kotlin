package kau.sohothackathon.compileerror.domain.model

import android.net.Uri

data class MediaFile(
    val name: String,
    val data: String,
    val contrntUri: Uri,
    val mediaType: MediaType
) {

}