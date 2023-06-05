package kau.sohothackathon.compileerror.domain

import kau.sohothackathon.compileerror.domain.model.MediaFile

interface FileRepository {

    fun fetchAllMediaFiles(): List<MediaFile>
}