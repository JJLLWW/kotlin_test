package ugh

import java.util.Optional

// slightly verbose interface, should it strip whitespace if this mangles
// the position in the string
class Tokeniser(val lang: Language = Language()) {
    data class Success(val tokens: List<Token>)
    enum class ErrorType { NONE, INVALID_TOKEN }
    data class Result(
        val result: Optional<Success>,
        val errType: ErrorType,
        val errMsg: String)
    fun Tokenise(line: String): Result {
        val stripped = line.replace("\\s+".toRegex(), "")
        var pos = 0
        var tokens: List<Token> = listOf()
        while (pos < stripped.length) {
            var firstMatch: MatchResult? = null
            var matchRegex: Regex = "".toRegex()
            for (reg: Regex in lang.LangMap.keys) {
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
            val type = lang.LangMap.getOrDefault(matchRegex, Token.Type.NONE)
            assert(type != Token.Type.NONE)
            when (Token.GetKotlinType(type)) {
                Token.KotlinType.INT -> {
                    tokens += Token(type, lang.AsInt(word, type).get())
                 }
                else -> {
                    tokens += Token(type)
                }
            }
            pos = firstMatch.range.last + 1
        }
        return Result(
            result = Optional.of(Success(tokens)),
            errType = ErrorType.NONE,
            errMsg = ""
        )
    }
}