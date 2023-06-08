package ugh

import java.util.Optional

// A generic token doesn't work well with collections so need to resort to this hackery
class Token(val type: Type) {
    enum class Type { LPAR, RPAR, NUM, ADD, MULT }
    enum class KotlinType { NONE, INT } // the Kotlin type of this token
    constructor(type: Type, int: Int) : this(type) { StoredInt = Optional.of(int) }
    var StoredInt: Optional<Int> = Optional.empty()
        private set
    companion object {
        fun GetKotlinType(type: Type): KotlinType {
            return when (type) {
                Type.NUM -> KotlinType.INT
                else -> KotlinType.NONE
            }
        }
    }
}
