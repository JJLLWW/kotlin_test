package com.ugh

fun main() {
    val tokeniser = Tokeniser()
    val res = tokeniser.tokenise(" ( 1 + 2   )  ")
    println(res)
}