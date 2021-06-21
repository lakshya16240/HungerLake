package com.example.hungerlake.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.hungerlake.models.Item

class DiffUtilCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.venue.id == newItem.venue.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.venue.id == newItem.venue.id
                && oldItem.venue.name == newItem.venue.name
    }
}