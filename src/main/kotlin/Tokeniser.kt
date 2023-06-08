package ugh

import java.util.Optional

// slightly verbose interface
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
        while (pos < stripped.length) {
            var firstMatch: MatchResult? = null
            for (reg: Regex in lang.LangMap.keys) {
                val matches = reg.matchAt(line, pos)
                if(matches != null) {
                    firstMatch = matches
                    break
                }
            }
            if(firstMatch == null) {
                return Result(
                    result = Optional.empty(),
                    errType = ErrorType.INVALID_TOKEN,
                    errMsg = "Invalid token at position $pos")
            }

        }
        return Result(Optional.empty(), ErrorType.INVALID_TOKEN, "oh no")
    }
}