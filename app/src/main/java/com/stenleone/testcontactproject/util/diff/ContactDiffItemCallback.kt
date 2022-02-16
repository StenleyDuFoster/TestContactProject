package com.stenleone.testcontactproject.util.diff

import androidx.recyclerview.widget.DiffUtil
import com.stenleone.testcontactproject.domain.entity.ContactEntity

class ContactDiffItemCallback: DiffUtil.ItemCallback<ContactEntity>() {

    override fun areItemsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ContactEntity, newItem: ContactEntity): Boolean =
        oldItem.equals(newItem)

}