package com.stenleone.testcontactproject.core

interface EntityModelMapper<ENTITY, MODEL> {

    fun toEntity(model: MODEL): ENTITY

    fun toModel(entity: ENTITY): MODEL {
        throw NotImplementedError()
    }

}