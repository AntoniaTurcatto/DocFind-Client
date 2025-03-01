package org.example.model

import java.util.*

class Doctor(override val id: UUID?,
             override var name: String,
             var role: Role) : Person {

    init {
        name = name.lowercase().replaceFirstChar { it.uppercase() }
    }

    override fun toString(): String {
        return "Doctor{id=${id}, name=${name}, role=${role}}"
    }
}