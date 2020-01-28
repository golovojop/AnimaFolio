package k.s.yarlykov.animafolio.ui.photogallery

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import k.s.yarlykov.animafolio.R
import k.s.yarlykov.animafolio.application.App
import k.s.yarlykov.animafolio.application.DependencySource
import k.s.yarlykov.animafolio.domain.Photo
import kotlinx.android.synthetic.main.activity_parallax_photo.*

class PhotoParallaxActivity : AppCompatActivity() {

    private val scrollEventsEmitter: BehaviorSubject<Int> = BehaviorSubject.create()
    private val model = mutableListOf<Photo>()

    lateinit var disposable : Disposable
    lateinit var dependencySource: DependencySource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parallax_photo)

        dependencySource = App.getDependecies()
        loadModel(this::initRecyclerView)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    /**
     * Загрузить фотки
     */
    private fun loadModel(onCompleteListener : (context: Context, data : List<Photo>) -> Unit) {
        disposable = dependencySource
            .getPhotoReposytory()
            .galleryStream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{list ->
                model.clear()
                model.addAll(list)
                onCompleteListener(this, model)
            }
    }

    private fun initRecyclerView(context: Context, data : List<Photo>) {

        with(photoRecyclerView){
            layoutManager = LinearLayoutManager(context)
            adapter = PhotoParallaxAdapter(context, data, scrollEventsEmitter.hide())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                    /**
                     * Как я понял в dy (dx) получаем "длину" проделанного скрола.
                     * Она может быть отрицательной и положительной в зависимости от направления
                     *
                     * dy > 0 [Scrolled Downwards] Крутим к нижнему элементу списка
                     * dy < 0 [Scrolled Upwards] Крутим в начало, к верхнему элементу
                     */

                    if (dy != 0) {
                        scrollEventsEmitter.onNext(dy)
                    }
                }
            })
        }
    }
}