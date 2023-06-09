package com.ugh

import java.util.Optional

// shouldn't this be with the test code - don't know if it can be used for
// anything else

/**
 * Reads from a string of the form LPAR NUM_1 ADD NUM_2 RPAR -> (1+2).
 * Will throw on bad input as mainly meant to be used for testing.
 */
internal fun tokensFromString(string: String): List<Token> {
    val words = string.split(' ')
    var tokens: List<Token> = listOf()
    for (word in words) {
        if(word.startsWith("NUM")) {
            val int: Int = word.split('_')[1].toInt()
            tokens += Token(Token.Type.NUM, int)
        } else {
            val token: Token = when (word) {
                "LPAR" -> Token(Token.Type.LPAR)
                "RPAR" -> Token(Token.Type.RPAR)
                "ADD" -> Token(Token.Type.ADD)
                "MULT" -> Token(Token.Type.MULT)
                else -> Token(Token.Type.NONE)
            }
            assert(token.type != Token.Type.NONE)
            tokens += token
        }
    }
    return tokens
}

// A generic token doesn't work well with collections so need to resort to this hackery
class Token(val type: Type) {
    enum class Type { NONE, LPAR, RPAR, NUM, ADD, MULT }
    enum class KotlinType { NONE, INT } // the Kotlin type of this token
    constructor(type: Type, int: Int) : this(type) { StoredInt = Optional.of(int) }
    var StoredInt: Optional<Int> = Optional.empty()
        private set
    companion object {
        fun getKotlinType(type: Type): KotlinType {
            return when (type) {
                Type.NUM -> KotlinType.INT
                else -> KotlinType.NONE
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if(other == null || other.javaClass != this.javaClass) {
            return false
        }
        val other2 = other as Token
        return type == other2.type && StoredInt == other2.StoredInt
    }
}
