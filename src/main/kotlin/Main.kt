package ugh

fun main() {
    val tokeniser = Tokeniser()
    val res = tokeniser.Tokenise(" ( 1 + 2   )  ")
    println(res)
}