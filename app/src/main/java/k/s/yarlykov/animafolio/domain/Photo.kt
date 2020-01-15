package k.s.yarlykov.animafolio.domain

import android.graphics.Bitmap

data class Photo(
    var id: Int,
    var bitmap: Bitmap,
    var description: String,
    var favorite: Boolean,
    var likes: Int
)