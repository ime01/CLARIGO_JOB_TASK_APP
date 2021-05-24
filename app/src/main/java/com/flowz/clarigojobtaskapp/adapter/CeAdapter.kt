package com.flowz.clarigojobtaskapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.flowz.clarigojobtaskapp.R
import com.flowz.clarigojobtaskapp.databinding.CeEmployeeItemBinding
import com.flowz.clarigojobtaskapp.model.ClarigoEmployee



class CeAdapter  (val listener: RowClickListener)  :ListAdapter<ClarigoEmployee, CeAdapter.CeViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  CeViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.ce_employee_item, parent, false)
        return CeViewHolder(CeEmployeeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: CeViewHolder, position: Int) {

        val currentItem = getItem(position)

        holder.binding.apply {

            holder.itemView.apply {
                ceName.text = "${currentItem?.name}"
                ceEmail.text = "${currentItem?.email}"
                ceDateOfBirth.text = "${currentItem?.dateOfBirth}"

                val imageurl = currentItem?.profilePicture

                Glide.with(cePassportPhoto)
                    .load(imageurl)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_person_24)
                    .error(R.drawable.ic_baseline_person_24)
                    .fallback(R.drawable.ic_baseline_person_24)
                    .into(cePassportPhoto)

////                loading image here with COIL libarary
//                cePassportPhoto.load(imageurl) {
//                    error(R.drawable.ic_baseline_person_24)
//                    placeholder(R.drawable.ic_baseline_person_24)
//                    crossfade(true)
//                    crossfade(1000)
//                }
            }

        }
    }

    interface RowClickListener{
        fun onEditClickListener(clarigoEmployee: ClarigoEmployee)
        fun onDeleteClickListener(clarigoEmployee: ClarigoEmployee)
    }


    inner class CeViewHolder(val binding: CeEmployeeItemBinding): RecyclerView.ViewHolder(binding.root){

        init {
            binding.ceEdit.setOnClickListener {
                val item = getItem(adapterPosition)
                listener.onEditClickListener(item)
            }
            binding.ceDelete.setOnClickListener {
                val item = getItem(adapterPosition)
                listener.onDeleteClickListener(item)
            }
        }
    }


}