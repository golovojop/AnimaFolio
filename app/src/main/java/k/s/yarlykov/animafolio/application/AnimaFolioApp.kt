package k.s.yarlykov.animafolio.application

import android.app.Application
import k.s.yarlykov.animafolio.R
import k.s.yarlykov.animafolio.repository.localstorage.LocalStorage
import k.s.yarlykov.animafolio.repository.photo.PhotoRepository
import k.s.yarlykov.animafolio.repository.photo.PhotoRepositoryImpl
import k.s.yarlykov.animafolio.repository.localstorage.LocalStorageImpl

class AnimaFolioApp : Application(), DependencySource {

    private lateinit var localStorage: LocalStorage
    private lateinit var photoRepository: PhotoRepository

    override fun onCreate() {
        super.onCreate()
        localStorage = LocalStorageImpl(
            this,
            R.array.month_pics,
            R.array.months,
            R.drawable.bkg_05_may)

        photoRepository = PhotoRepositoryImpl(localStorage)
    }

    override fun getPhotoReposytory(): PhotoRepository {
        return photoRepository
    }
}