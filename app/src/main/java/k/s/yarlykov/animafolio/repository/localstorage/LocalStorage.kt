package k.s.yarlykov.animafolio.repository.localstorage

import io.reactivex.Single
import k.s.yarlykov.animafolio.domain.Photo

interface LocalStorage {
    fun connectToBitmapStream(): Single<List<Photo>>
    fun addPhoto(photo: Photo)
    fun deletePhoto(photo: Photo)
    fun populateCache()
}