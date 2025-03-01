package org.example.model

import java.util.*

class Patient(override val id: UUID?,
              override var name: String,
              private var age:Int,
              private var address:String = "Unknown" //default value if not input
    ) : Person {

    init {
        val formatString: (String) -> String = {
            it.trim().lowercase().replaceFirstChar { c -> c.uppercase()}
        }
        name = formatString(name)
        address = formatString(address)
    }

    override fun toString(): String {
        return "Patient{id=${id}, name=${name}, age=${age}, address=${address}}"
    }
}