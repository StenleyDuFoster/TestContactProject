package com.stenleone.testcontactproject.domain.mapper

import com.stenleone.testcontactproject.core.EntityModelMapper
import com.stenleone.testcontactproject.domain.entity.ContactEntity
import com.stenleone.testcontactproject.domain.model.ContactModel
import com.stenleone.testcontactproject.util.exception.orZero
import javax.inject.Inject

class ContactMapper @Inject constructor() : EntityModelMapper<ContactEntity, ContactModel> {

    override fun toEntity(model: ContactModel): ContactEntity {
        with(model) {
            return ContactEntity(
                companyName = companyName.orEmpty(),
                createdAt = createdAt.orEmpty(),
                department = department.orEmpty(),
                email = email.orEmpty(),
                id = id.orEmpty(),
                name = name.orEmpty(),
                number = number.orZero(),
                surname = surname.orEmpty()
            )
        }
    }

}