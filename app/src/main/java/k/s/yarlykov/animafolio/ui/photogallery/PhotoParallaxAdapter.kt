package k.s.yarlykov.animafolio.ui.photogallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import k.s.yarlykov.animafolio.R
import k.s.yarlykov.animafolio.domain.Photo

class PhotoParallaxAdapter(
    private val context: Context,
    private val photos: List<Photo>,
    private val scrollObservable: Observable<Int>
) : RecyclerView.Adapter<PhotoParallaxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.layout_item_parallax_photo,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        private val motionView: VerticalMotionListener =
            view.findViewById<ParallaxListItem>(R.id.motionLayout)

        private val ivPhoto = view.findViewById<ImageView>(R.id.ivPhoto)
        private val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

        init {
            val d = scrollObservable.subscribe {

                if (adapterPosition == 0) {
                    motionView.setDebugFlag(true)
                } else {
                    motionView.setDebugFlag(false)
                }

                motionView.onOffsetChanged(view.y)
            }
        }

        fun bind(photo: Photo) {
            motionView.onUpdateMaxOffset(motionView.minStartOffset)
            ivPhoto.setImageBitmap(photo.bitmap)
            tvDescription.text = photo.description
        }
    }
}