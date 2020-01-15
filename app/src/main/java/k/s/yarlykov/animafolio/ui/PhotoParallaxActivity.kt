package k.s.yarlykov.animafolio.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import k.s.yarlykov.animafolio.R
import k.s.yarlykov.animafolio.application.DependencySource
import k.s.yarlykov.animafolio.domain.Photo

class PhotoParallaxActivity : AppCompatActivity() {

    private val model = mutableListOf<Photo>()
    lateinit var disposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax_photo)

        initRecyclerView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun initRecyclerView(context: Context) {

        val dependencies = (application as DependencySource).getPhotoReposytory()

        disposable = dependencies
            .galleryStream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{list ->
                model.clear()
                model.addAll(list)

            }
    }


}