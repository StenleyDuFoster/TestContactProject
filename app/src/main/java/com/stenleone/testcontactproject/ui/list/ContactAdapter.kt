package com.stenleone.testcontactproject.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stenleone.testcontactproject.core.BaseListAdapter
import com.stenleone.testcontactproject.core.RecyclerBinder
import com.stenleone.testcontactproject.databinding.ItemContactBinding
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import com.stenleone.testcontactproject.util.diff.ContactDiffItemCallback

class ContactAdapter : BaseListAdapter<ContactEntity, ContactAdapter.ContactHolder>(ContactDiffItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder =
        ContactHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    inner class ContactHolder(private val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root), RecyclerBinder {

        init {
            binding.rootCard.throttleClicks {
                clickListener.invoke(currentList[bindingAdapterPosition])
            }
        }

        override fun bind() {
            binding.contact = currentList[bindingAdapterPosition]
        }

    }

}