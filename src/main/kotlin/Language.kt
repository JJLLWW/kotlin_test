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
    val LangMap: HashMap<Regex, Token.Type> = DefaultMap
) {
    fun AsInt(word: String, type: Token.Type): Optional<Int> {
        if(Token.GetKotlinType(type) != Token.KotlinType.INT) {
            return Optional.empty()
        }
        when (type) {
            Token.Type.NUM -> {
                val int = word.toIntOrNull()
                return if(int == null) Optional.empty() else Optional.of(int)
            }
            else -> {
                return Optional.empty()
            }
        }
    }
}