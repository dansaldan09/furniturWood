package com.example.mebelkayu.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mebelkayu.R
import com.example.mebelkayu.model.Furniture

class FurnitureListAdapter (
    private val onItemClickListener: (Furniture) -> Unit,
): ListAdapter<Furniture, FurnitureListAdapter.FurnitureViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FurnitureViewHolder {
        return FurnitureViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FurnitureViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
        holder.itemView.setOnClickListener {
            onItemClickListener(current)
        }
    }


    class FurnitureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val furnitureName = itemView.findViewById<android.widget.TextView>(R.id.nameTextView)
        private val furnitureAddress =
            itemView.findViewById<android.widget.TextView>(R.id.addressTextView)
        private val furnitureInformation =
            itemView.findViewById<android.widget.TextView>(R.id.informationtextView)

        //    private val furnitureContent = itemView.findViewById<android.widget.TextView>(R.id.contentTextView)
        fun bind(current: Furniture?) {
            furnitureName.text = current?.name
            furnitureAddress.text = current?.address
            furnitureInformation.text = current?.information
        }

        companion object {
            fun create(parent: ViewGroup): FurnitureListAdapter.FurnitureViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_furniture, parent, false)
                return FurnitureViewHolder(view)
            }
        }

    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Furniture>() {
            override fun areItemsTheSame(oldItem: Furniture, newItem: Furniture): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Furniture, newItem: Furniture): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}