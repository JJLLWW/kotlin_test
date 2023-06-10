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
    fun addChilds(nodes: List<ExprNode>): Unit {
        for (node in nodes) {
            addChild(node)
        }
    }
    // return bool which determines if function has succeeded
    fun liftChildToRoot(i: Int): Boolean {
        if(type != Type.ROOT || i !in children.indices) {
            return false
        }
        // sublist(i, j) gives elements with indeces in [i, j)
        val left = children.subList(0, i)
        val right = children.subList(i+1, children.size)
        val node: ExprNode = children[i]
        if(left.isEmpty() || right.isEmpty()) {
            return false
        }
        children.clear()
        children.add(ExprNode.getRoot())
        children.add(ExprNode.getRoot())
        children[0].addChilds(left)
        children[1].addChilds(right)
        return true
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