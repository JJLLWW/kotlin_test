package ugh

import java.util.Optional

/**
 * A "Language" represents the regular expressions the tokeniser uses
 * to tokenise an input line and the conversions used to get the kotlin
 * type from the token types.
 */

private val DefaultMap: HashMap<Regex, Token.Type> = hashMapOf(
    Pair("\\(".toRegex(), Token.Type.LPAR),
    Pair("\\)".toRegex(), Token.Type.RPAR),
    Pair("\\+".toRegex(), Token.Type.ADD),
    Pair("\\*".toRegex(), Token.Type.MULT),
    Pair("[0-9]+".toRegex(), Token.Type.NUM),
)

class Language(
    val langMap: HashMap<Regex, Token.Type> = DefaultMap
) {
    fun asInt(word: String, type: Token.Type): Optional<Int> {
        if(Token.getKotlinType(type) != Token.KotlinType.INT) {
            return Optional.empty()
        }
        return when (type) {
            Token.Type.NUM -> {
                val int = word.toIntOrNull()
                if(int == null) Optional.empty() else Optional.of(int)
            }
            else -> {
                Optional.empty()
            }
        }
    }
}