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

class MenuListAdapter(
    private val context: Context,
    private val items: List<MenuItemData>,
    private val onClickCallback: (MenuItemData) -> Unit
) :
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

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {


        val tvTitle : TextView = view.findViewById(R.id.item_title)
        val ivIcon : ImageView = view.findViewById(R.id.item_preview)


        fun bind(itemData: MenuItemData) {
            view.setOnClickListener {
                onClickCallback(itemData)
            }

            tvTitle.text = itemData.title
            ivIcon.setImageDrawable(itemData.icon)
        }
    }
}