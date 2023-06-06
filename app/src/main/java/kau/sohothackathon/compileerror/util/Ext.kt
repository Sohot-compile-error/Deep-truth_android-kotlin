package kau.sohothackathon.compileerror.util

import android.media.MediaPlayer

fun checkDeepTruth(
    name: String,
    mediaType: String,
    contentUri: String
): Boolean {
    return name.contains("clone")
}
