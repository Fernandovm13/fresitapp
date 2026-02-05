package com.fervelez.fresitaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fervelez.fresitaapp.R
import com.fervelez.fresitaapp.model.Fruit

class FruitAdapter(private val ctx: Context, private var items: MutableList<Fruit>) : RecyclerView.Adapter<FruitAdapter.VH>() {

    fun update(list: List<Fruit>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_fruit, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val it = items[position]
        holder.tvNombre.text = it.nombre ?: "-"
        holder.tvCientifico.text = it.nombre_cientifico ?: "-"
        holder.tvTemp.text = it.temporada ?: "-"
        holder.tvClas.text = it.clasificacion ?: "-"

        val base = (ctx.getString(R.string.base_url_no_slash))
        val url = if (!it.imagen_path.isNullOrEmpty()) "$base/uploads/${it.imagen_path}" else null

        if (url != null) Glide.with(ctx).load(url).into(holder.iv)
        else holder.iv.setImageResource(R.drawable.ic_baseline_local_grocery_store_24)
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val iv: ImageView = v.findViewById(R.id.ivFruit)
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvCientifico: TextView = v.findViewById(R.id.tvCientifico)
        val tvTemp: TextView = v.findViewById(R.id.tvTemporada)
        val tvClas: TextView = v.findViewById(R.id.tvClasificacion)
    }
}
