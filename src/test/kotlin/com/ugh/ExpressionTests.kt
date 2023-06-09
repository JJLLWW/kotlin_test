package com.ugh

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ExpressionTests {
    @Test fun sanityCheck() {
        val expr = ExprNode(ExprNode.Type.ROOT)
        expr.addChild(ExprNode(ExprNode.Type.NUM, 1))
        expr.addChild(ExprNode(ExprNode.Type.NUM, 2))
    }
}