package com.sqbrains.arithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {

    @Test
    void testExpressionCreation() {
        assertThrows(InvalidExpressionException.class, () -> new Expression(""));
        assertThrows(InvalidExpressionException.class, () -> new Expression("-"));
        assertThrows(InvalidExpressionException.class, () -> new Expression("3+"));
        assertThrows(InvalidExpressionException.class, () -> new Expression(")3+"));

        Expression expression = new Expression("33");
        assertEquals(33.0f, expression.getTokens().get(0).getLexeme());

        expression = new Expression("-33");
        assertEquals(Token.MINUS, expression.getTokens().get(0).getLexeme());
        assertNotEquals(Token.PLUS, expression.getTokens().get(0).getLexeme());
        assertEquals(33.0f, expression.getTokens().get(1).getLexeme());

        expression = new Expression("+33");
        assertEquals(Token.PLUS, expression.getTokens().get(0).getLexeme());
        assertNotEquals(Token.MINUS, expression.getTokens().get(0).getLexeme());
        assertEquals(33.0f, expression.getTokens().get(1).getLexeme());
    }

    @Test
    void testExpressionCompilation() {
        Expression expression = new Expression("6 / 2 * (2 + 1)");
        assertEquals(6.0f, expression.compile().getLexeme());

        //expression = new Expression("(4 + 2) * ((-1 + 2) / 2)");
        //assertEquals(4.0f, expression.compile().getLexeme());
    }
}