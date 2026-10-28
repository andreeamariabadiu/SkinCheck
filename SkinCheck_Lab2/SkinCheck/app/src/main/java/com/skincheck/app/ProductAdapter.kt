package com.skincheck.app

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.skincheck.app.databinding.ItemProductBinding

class ProductAdapter(
    private var items: List<Product>,
    private val onClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.VH>() {

    class VH(val b: ItemProductBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val p = items[position]
        val ctx = holder.b.root.context
        holder.b.tvName.text = p.name
        holder.b.tvBrand.text = p.brand
        holder.b.tvRating.text = ctx.getString(p.rating.labelRes)
        holder.b.viewDot.backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(ctx, p.rating.colorRes))
        holder.b.imgFav.visibility = if (p.isFavorite) View.VISIBLE else View.GONE
        holder.b.root.setOnClickListener { onClick(p) }
    }

    fun update(newItems: List<Product>) {
        items = newItems
        notifyDataSetChanged()
    }
}
