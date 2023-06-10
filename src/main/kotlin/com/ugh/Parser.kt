package com.ugh

import java.util.Optional

/**
 * The intermediate representation of a stream of tokens, helper
 * to determine if valid and the starting and ending positions of
 * each sub-expression
 */
internal class TokensIR(val tokens: List<Token>) {
    var closingParen: MutableMap<Int, Int> = mutableMapOf()
        private set
    var balancedParens: Boolean = true
        private set
    var repeatedOperators: Boolean = false
        private set
    operator fun get(i: Int): Token {
        return tokens[i]
    }
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
                        closingParen[stack.removeLast()] = i
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

// will probably have some configuration info later so don't make parseTokens static
class Parser {
    enum class ErrorType { NONE, UNBAL_PAREN, PRE_PARSE }
    data class Result(
        val result: Optional<ExprNode>,
        val errType: ErrorType,
        val errMsg: String
    )
    fun parseTokens(tokens: List<Token>): Result {
        val tokensIR = TokensIR(tokens)
        if(!tokensIR.isValid()) {
            return Result(
                Optional.empty(),
                ErrorType.UNBAL_PAREN,
                "unbalanced parens")
        }
        val root: ExprNode = recursivePreParse(tokensIR, 0, tokensIR.tokens.size)
        if(root.type == ExprNode.Type.NIL) {
            return Result(
                Optional.empty(),
                ErrorType.PRE_PARSE,
                "pre parse"
            )
        }
        // TODO: flatten to binary tree
        return Result(Optional.of(root), ErrorType.NONE,  "")
    }
    // will work correctly on well formed input, but if the input is ill-formed the behaviour
    // is a bit strange
    private fun recursivePreParse(tokensIR: TokensIR, lower: Int, upper: Int): ExprNode {
        if(upper <= lower) {
            return ExprNode.getNIL()
        }
        if(lower + 1 == upper) {
            return ExprNode.fromToken(tokensIR[lower])
        }
        val root = ExprNode.getRoot()
        var i = lower
        while(i < upper) {
            // should implement index access operator
            if(tokensIR[i].type == Token.Type.LPAR) {
                val new_r = tokensIR.closingParen[i] as Int
                root.addChild(recursivePreParse(
                    tokensIR, i+1, new_r
                ))
                i = 1 + new_r
            } else {
                root.addChild(ExprNode.fromToken(tokensIR[i]))
                i++
            }
        }
        return root
    }
    // this needs context information about which operators are higher precedence
    // which I havent implemented yet
    private fun flattenTree(root: ExprNode) {
        return
    }
}