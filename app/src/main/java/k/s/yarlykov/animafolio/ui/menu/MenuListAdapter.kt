package k.s.yarlykov.animafolio.ui.menu

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

class MenuListAdapter(private val context: Context) :
    RecyclerView.Adapter<MenuListAdapter.ViewHolder>() {

    private val model = mutableListOf<MenuItemData>()

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
        return model.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(model[position])
        setAnimation(holder.itemView, position)
    }

    fun updateModel(li : List<MenuItemData>) {
        with(model) {
            clear()
            addAll(li)
        }
        notifyDataSetChanged()
    }

    private fun setAnimation(view: View, position: Int) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        animation.duration = position.toLong() * 50 + 200
        view.startAnimation(animation)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.item_title)
        private val ivIcon: ImageView = view.findViewById(R.id.item_preview)

        fun bind(itemData: MenuItemData) {

            // Цветовое оформление
            fun decorateItemView() {
                if(itemData.isActive) {
                    view.setBackgroundResource(R.drawable.side_nav_menu_item)
                    tvTitle.setTextColor(context.getColor(android.R.color.white))
                    ivIcon.setColorFilter(context.getColor(android.R.color.white))
                } else {
                    view.setBackgroundColor(context.getColor(android.R.color.white))
                    tvTitle.setTextColor(context.getColor(android.R.color.black))
                    ivIcon.setColorFilter(context.getColor(android.R.color.black))
                }
            }

            // Контент в элементах
            view.setOnClickListener {
                itemData.clickHandler(itemData.position)
            }
            tvTitle.text = itemData.title
            ivIcon.setImageDrawable(itemData.icon)

            decorateItemView()
        }
    }
}