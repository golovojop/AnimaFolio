package k.s.yarlykov.animafolio.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import k.s.yarlykov.animafolio.R

class MenuListAdapter(private val context: Context, private val itemQty: Int) :
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
        return itemQty
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setAnimation(holder.itemView, position)
//            holder.itemView.setOnClickListener { view ->
//            }
    }

    private fun setAnimation(view: View, position: Int) {
        val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        animation.duration = position.toLong() * 50 + 200
        view.startAnimation(animation)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}