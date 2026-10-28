package com.skincheck.app

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skincheck.app.databinding.ItemIngredientBinding

class IngredientAdapter(
    private val items: List<Ingredient>,
    private val onClick: (Ingredient) -> Unit
) : RecyclerView.Adapter<IngredientAdapter.VH>() {

    class VH(val b: ItemIngredientBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val i = items[position]
        val ctx = holder.b.root.context
        holder.b.tvName.text = i.name
        holder.b.tvFunction.text = i.function
        holder.b.viewDot.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(ctx, i.rating.colorRes))
        holder.b.root.setOnClickListener { onClick(i) }
    }
}
