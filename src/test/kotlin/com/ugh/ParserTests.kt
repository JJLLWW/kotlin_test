package com.ugh

import org.junit.jupiter.api.Test
class ParserTests {
    @Test fun tokenIRSanity() {
        // don't want the test to be dependent on the tokeniser working
        val tokens = tokensFromString("LPAR ADD LPAR ADD RPAR ADD RPAR")
        val IR = TokensIR(tokens)
        assert(IR.isValid())
        assert(IR.closingParen[0] == 6)
        assert(IR.closingParen[2] == 4)
    }
    @Test fun parserSanity() {
        val tokens = tokensFromString("LPAR ADD LPAR ADD RPAR ADD RPAR")
        val parser = Parser()
        val enode = parser.parseTokens(tokens)
        assert(true)
    }
}