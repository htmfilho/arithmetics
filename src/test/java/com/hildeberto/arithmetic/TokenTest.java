package com.hildeberto.arithmetic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {
    @Test
    void testTokenizeWithNoTokens() {
        List<Token> tokens = Token.tokenize("2 * 8 + 3 / 4 - 5");
        assertEquals(9, tokens.size());
        assertEquals(2.0f, tokens.get(0).getValue());
        assertEquals('*', tokens.get(1).getValue());
        assertEquals(8f, tokens.get(2).getValue());
        assertEquals('+', tokens.get(3).getValue());
        assertEquals(3f, tokens.get(4).getValue());
        assertEquals('/', tokens.get(5).getValue());
        assertEquals(4f, tokens.get(6).getValue());
        assertEquals('-', tokens.get(7).getValue());
        assertEquals(5f, tokens.get(8).getValue());

        tokens = Token.tokenize("6/2*(2+1)");
        assertEquals(9, tokens.size());

        tokens = Token.tokenize("6  /  (2*(2 + 1 ))");
        assertEquals(11, tokens.size());

        tokens = Token.tokenize("6  /  (tax*(2 + debt))");
        assertEquals(11, tokens.size());
        assertEquals("tax", tokens.get(3).getValue());
        assertEquals("debt", tokens.get(8).getValue());

        assertThrows(UnrecognizedTokenException.class, () -> Token.tokenize("6  /  (2*(2 + 1 )) %"));

        tokens = Token.tokenize("6 / 2 * (-2 + 1)");
        assertEquals(10, tokens.size());
    }

    @Test
    void isNumber() {
        assertTrue(Token.isNumber("439"));
        assertTrue(Token.isNumber("0.439"));
        assertTrue(Token.isNumber("345."));

        assertFalse(Token.isNumber(null));
        assertFalse(Token.isNumber(""));
        assertFalse(Token.isNumber("+"));
        assertFalse(Token.isNumber("abc1"));
        assertFalse(Token.isNumber("123+"));
        assertFalse(Token.isNumber("123 +"));
        assertFalse(Token.isNumber("123qw"));
    }

    @Test
    void isSymbol() {
        assertTrue(Token.isSymbol("+"));
        assertTrue(Token.isSymbol("-"));
        assertTrue(Token.isSymbol("*"));
        assertTrue(Token.isSymbol("/"));
        assertTrue(Token.isSymbol("("));
        assertTrue(Token.isSymbol(")"));
        assertTrue(Token.isSymbol("+ "));

        assertFalse(Token.isSymbol(null));
        assertFalse(Token.isSymbol(""));
        assertFalse(Token.isSymbol("++"));
        assertFalse(Token.isSymbol("kio"));
        assertFalse(Token.isSymbol("9.8"));
    }

    @Test
    void isVariable() {
        assertTrue(Token.isVariable("score_1"));

        assertFalse(Token.isVariable(null));
        assertFalse(Token.isVariable(""));
        assertFalse(Token.isVariable("22.3"));
        assertFalse(Token.isVariable("+"));
        assertFalse(Token.isVariable("asd 1"));
        assertFalse(Token.isVariable("asd_"));
        assertFalse(Token.isVariable("asd+"));
    }
}