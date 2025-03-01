package org.example.model

enum class Role(val codRole:Int) {
    SURGEON(0),
    ORTHOPEDIST(1),
    PHARMACIST(2),
    OTORHINOLARYNGOLOGIST(3),
    OPHTHALMOLOGIST(4);

    companion object{
        fun getRoleEnum(codRole:Int):Role?{
            return values().find { it.codRole == codRole }
        }

        fun getRoleEnum(role:String):Role?{
            return runCatching {
                valueOf(role.uppercase())
            }.getOrNull()
        }

    }
}