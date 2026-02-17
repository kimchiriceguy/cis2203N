package com.example.study_buddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
        private val items: List<Item>
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.itemName)
    val email: TextView = view.findViewById(R.id.itemEmail)
    val image: ImageView = view.findViewById(R.id.itemImage)
}

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): View {
    val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
    return ViewHolder(view)
}

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val item = items[position]
    holder.name.text = item.name
    holder.email.text = item.email
    holder.image.setImageResource(item.imageResId)
}

override fun getItemCount(): Int = items.size
}
