package k.s.yarlykov.animafolio.ui.fragments.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

class ParallaxFragment : Fragment() {

    private val scrollEventsEmitter: BehaviorSubject<Int> = BehaviorSubject.create()
    private val model = mutableListOf<Photo>()

    lateinit var disposable: Disposable
    lateinit var dependencySource: DependencySource
    lateinit var currentContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parallax, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dependencySource = App.getDependecies()
        loadModel(this::initRecyclerView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.currentContext = context
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
    private fun loadModel(onCompleteListener : (ctx: Context, data : List<Photo>) -> Unit) {
        disposable = dependencySource
            .getPhotoReposytory()
            .galleryStream()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{list ->
                model.clear()
                model.addAll(list)
                onCompleteListener(currentContext, model)
            }
    }

    private fun initRecyclerView(context: Context, data : List<Photo>) {

        with(photoRecyclerView){
            layoutManager = LinearLayoutManager(context)
            adapter = PhotoParallaxAdapter(
                context,
                data,
                scrollEventsEmitter.hide()
            )
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