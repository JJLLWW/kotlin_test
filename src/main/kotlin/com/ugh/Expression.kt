package com.ugh

internal class ExprNode(type: Type) {
    constructor(type: Type, value: Int) : this(type) {
        this.value = value
    }
    var value: Int = -1
    enum class Type { ROOT, ADD, MUL, NUM }
    var children: MutableList<ExprNode> = mutableListOf()
    fun addChild(node: ExprNode): Unit {
        children.add(node)
    }
}