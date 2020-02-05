package k.s.yarlykov.animafolio.repository.photo

import io.reactivex.Observable
import k.s.yarlykov.animafolio.domain.Photo

interface PhotoRepository {
    fun galleryStream(): Observable<List<Photo>>
    fun favoritesStream(): Observable<List<Photo>>
    fun onUpdate(position: Int, photo: Photo)
}