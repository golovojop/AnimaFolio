package k.s.yarlykov.animafolio.repository.photo

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import k.s.yarlykov.animafolio.domain.Photo
import k.s.yarlykov.animafolio.repository.localstorage.LocalStorage

class PhotoRepositoryImpl(localStorage: LocalStorage) :
    PhotoRepository {

    private val liveData = BehaviorSubject.create<List<Photo>>()
    private val compositeDisposable = CompositeDisposable()

    init {

        // Инициировать подкачку картинок с диска
        localStorage.populateCache()

        with(compositeDisposable) {

            // Собрать Bitmap'ы картинок и в объекты Photo и результирующий массив
            // эмиттировать через liveData.
            add(
                localStorage.connectToBitmapStream()
                    .subscribe { photos ->
                        liveData.onNext(photos)
                    }
            )
        }
    }

    // PhotoRepository::galleryStream()
    override fun galleryStream(): Observable<List<Photo>> = liveData.hide()

    // PhotoRepository::favoritesStream()
    override fun favoritesStream(): Observable<List<Photo>> =
        liveData
            .hide()
            .map { list ->
                list.filter { photo ->
                    photo.favorite
                }
            }

    override fun onUpdate(position: Int, photo: Photo) {
        // Заменить элемент в списке
        liveData.value?.let { listPrev ->
            liveData.onNext(
                listPrev.map { p ->
                    if (p.id == photo.id) photo else p
                }
            )
        }
    }
}