package com.ugh

class ExprNode(var type: Type) {
    constructor(type: Type, value: Int) : this(type) {
        this.value = value
    }
    var value: Int = -1
    enum class Type { ROOT, ADD, MUL, NUM, NIL }
    var children: MutableList<ExprNode> = mutableListOf()
    fun addChild(node: ExprNode): Unit {
        children.add(node)
    }
    // convert token types to node types
    companion object {
        fun getRoot(): ExprNode {
            return ExprNode(Type.ROOT)
        }
        fun getNIL(): ExprNode {
            return ExprNode(Type.NIL)
        }

        /**
         * (!) only expects to be used on non-paren tokens (!)
         */
        fun fromToken(token: Token): ExprNode {
            assert(token.type != Token.Type.LPAR)
            assert(token.type != Token.Type.RPAR)
            return when (token.type) {
                Token.Type.NUM -> ExprNode(Type.NUM, token.StoredInt.get() as Int)
                Token.Type.MULT -> ExprNode(Type.MUL)
                Token.Type.ADD -> ExprNode(Type.ADD)
                else -> ExprNode(Type.NIL)
            }
        }
    }
}