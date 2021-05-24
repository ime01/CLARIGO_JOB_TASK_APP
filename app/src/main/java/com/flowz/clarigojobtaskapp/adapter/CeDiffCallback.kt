package com.flowz.clarigojobtaskapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee


class DiffCallback : DiffUtil.ItemCallback<ClarigoEmployee>(){
    override fun areItemsTheSame(oldItem: ClarigoEmployee, newItem: ClarigoEmployee): Boolean {
        return oldItem.personId == newItem.personId
    }
    override fun areContentsTheSame(oldItem: ClarigoEmployee, newItem: ClarigoEmployee): Boolean {
        return oldItem == newItem
    }
}