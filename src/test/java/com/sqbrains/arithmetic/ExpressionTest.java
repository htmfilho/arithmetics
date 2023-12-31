package com.sqbrains.arithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {

    @Test
    void testExpressionCreation() {
        assertThrows(InvalidExpressionException.class, () -> new Expression(""));
        assertThrows(InvalidExpressionException.class, () -> new Expression("-"));
        assertThrows(InvalidExpressionException.class, () -> new Expression("3+"));
        
        Expression expression = new Expression("33");
        assertEquals(33.0f, expression.getTokens().get(0).getLexeme());

        expression = new Expression("-33");
        assertEquals(TokenPattern.MINUS, expression.getTokens().get(0).getLexeme());
        assertNotEquals(TokenPattern.PLUS, expression.getTokens().get(0).getLexeme());
        assertEquals(33.0f, expression.getTokens().get(1).getLexeme());

        expression = new Expression("+33");
        assertEquals(TokenPattern.PLUS, expression.getTokens().get(0).getLexeme());
        assertNotEquals(TokenPattern.MINUS, expression.getTokens().get(0).getLexeme());
        assertEquals(33.0f, expression.getTokens().get(1).getLexeme());
    }

    @Test
    void testExpressionCompilation() {
        Expression expression = new Expression("(6 / 2) * (2 + 1)");
        assertEquals(6.0f, expression.compile().getLexeme());

        assertThrows(InvalidExpressionException.class, () -> new Expression("() * ((-1 + 2) / 2)").compile());
        assertThrows(InvalidExpressionException.class, () -> new Expression(")3+").compile());
    }

    @Test
    void testExpressionCompilationWithNegativeToken() {
        Expression expression = new Expression("-3");
        assertEquals(0f, expression.compile().getLeftChild().getLexeme());
    }
}