package com.ugh

import java.util.Stack

/**
 * The intermediate representation of a stream of tokens
 */
internal class TokensIR(val tokens: List<Token>) {

    var closingParen: MutableMap<Int, Int> = mutableMapOf()
        private set
    var balancedParens: Boolean = true
        private set
    var repeatedOperators: Boolean = false
        private set
    init {
        var stack = ArrayDeque<Int>()
        // TODO: also detect adjacent operators like 1 + * 2
        for(i: Int in tokens.indices) {
            when (tokens[i].type) {
                Token.Type.LPAR -> stack.add(i)
                Token.Type.RPAR -> {
                    if(stack.isEmpty()) {
                        balancedParens = false
                    } else {
                        closingParen[i] = stack.removeLast()
                    }
                }
                else -> {}
            }
        }
    }
    fun isValid(): Boolean {
        return balancedParens && !repeatedOperators
    }
}

class Parser {
}