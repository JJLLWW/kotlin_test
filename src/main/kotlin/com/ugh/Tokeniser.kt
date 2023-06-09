package com.ugh

import com.ugh.Language
import java.util.Optional

// slightly verbose interface, should it strip whitespace if this mangles
// the position in the string
class Tokeniser(private val lang: Language = Language()) {
    enum class ErrorType { NONE, INVALID_TOKEN }
    data class Result(
        val result: Optional<List<Token>>,
        val errType: ErrorType,
        val errMsg: String)
    fun tokenise(line: String): Result {
        val stripped = line.replace("\\s+".toRegex(), "")
        var pos = 0
        var tokens: List<Token> = listOf()
        while (pos < stripped.length) {
            var firstMatch: MatchResult? = null
            var matchRegex: Regex = "".toRegex()
            for (reg: Regex in lang.langMap.keys) {
                val matches = reg.matchAt(stripped, pos)
                if(matches != null) {
                    firstMatch = matches
                    matchRegex = reg
                    break
                }
            }
            if(firstMatch == null || firstMatch.range.first != pos) {
                return Result(
                    result = Optional.empty(),
                    errType = ErrorType.INVALID_TOKEN,
                    errMsg = "Invalid token at position $pos")
            }
            val word = firstMatch.value
            val type = lang.langMap.getOrDefault(matchRegex, Token.Type.NONE)
            assert(type != Token.Type.NONE)
            tokens += when (Token.getKotlinType(type)) {
                Token.KotlinType.INT -> {
                    Token(type, lang.asInt(word, type).get())
                 }
                else -> {
                    Token(type)
                }
            }
            pos = firstMatch.range.last + 1
        }
        return Result(
            result = Optional.of(tokens),
            errType = ErrorType.NONE,
            errMsg = ""
        )
    }
}