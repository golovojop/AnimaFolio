package k.s.yarlykov.animafolio.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import k.s.yarlykov.animafolio.R
import k.s.yarlykov.animafolio.domain.MenuItemData

class MenuListAdapter(private val context: Context, private val items: List<MenuItemData>) :
    RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.layout_item_menu,
                    parent,
                    false
                )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        setAnimation(holder.itemView, position)
//            holder.itemView.setOnClickListener { view ->
//            }
    }

    private fun setAnimation(view: View, position: Int) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        animation.duration = position.toLong() * 50 + 200
        view.startAnimation(animation)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvTitle = view.findViewById<TextView>(R.id.item_title)
        private val ivIcon = view.findViewById<ImageView>(R.id.item_preview)

        fun bind(itemData : MenuItemData) {
            tvTitle.text = itemData.title
            ivIcon.setImageDrawable(itemData.icon)
        }
    }
}