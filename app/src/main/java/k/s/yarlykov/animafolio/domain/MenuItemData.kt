package k.s.yarlykov.animafolio.domain

import android.graphics.drawable.Drawable

data class MenuItemData(
    val position : Int,
    val navId: Int,
    var isActive : Boolean,
    val title: String,
    val icon: Drawable,
    val clickHandler: (Int) -> Unit
)