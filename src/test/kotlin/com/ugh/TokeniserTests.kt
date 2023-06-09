package com.ugh

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import kotlin.jvm.optionals.getOrNull
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

data class TokenTestCase(val expect: List<Token>, val input: String)

class TokeniserTests {
    fun readCasesFromFile(fpath: File): List<TokenTestCase> {
        val reader = BufferedReader(InputStreamReader(FileInputStream(fpath)))
        val lines = reader.readLines()
        var cases: List<TokenTestCase> = listOf()
        for (line in lines) {
            val (expect, input) = line.split("|")
            cases += TokenTestCase(tokensFromString(expect), input)
        }
        reader.close()
        return cases
    }
    @Test fun testingOk() {
        assertTrue(true)
    }
    @Test fun equalsTest() {
        assert(Token(Token.Type.RPAR) == Token(Token.Type.RPAR))
        assert(Token(Token.Type.NUM, 1) == Token(Token.Type.NUM, 1))
        assert(Token(Token.Type.RPAR) != Token(Token.Type.LPAR))
        assert(Token(Token.Type.NUM, 1) != Token(Token.Type.NUM, 2))
    }
    @Test fun succeedOnCorrect() {
        // Mac only? windows uses \ seperator - is this being handled by the API?
        val cases = readCasesFromFile(File("src/test/resources/TokenTestCases.txt"))
        val tokeniser = Tokeniser()
        for (case in cases) {
            val output = tokeniser.tokenise(case.input)
            assert(output.result.getOrNull() != null)
            val tokens = output.result.get()
            assert(tokens.size == case.expect.size)
            for (i: Int in case.expect.indices) {
                assert(tokens[i] == case.expect[i])
            }
        }
    }
    @Test fun failOnIncorrect1() {
        val input = "1263712y%^R£@^%R^%"
        val tokeniser = Tokeniser()
        val output = tokeniser.tokenise(input)
        assert(output.result.getOrNull() == null)

    }
}