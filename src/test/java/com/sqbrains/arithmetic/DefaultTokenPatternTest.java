package com.sqbrains.arithmetic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTokenPatternTest {

    @Test
    void testIsNumber() {
        DefaultTokenPattern defaultTokenPattern = new DefaultTokenPattern();
        assertTrue(defaultTokenPattern.isNumber("439"));
        assertTrue(defaultTokenPattern.isNumber("0.439"));
        assertTrue(defaultTokenPattern.isNumber("345."));

        assertFalse(defaultTokenPattern.isNumber(null));
        assertFalse(defaultTokenPattern.isNumber(""));
        assertFalse(defaultTokenPattern.isNumber("+"));
        assertFalse(defaultTokenPattern.isNumber("abc1"));
        assertFalse(defaultTokenPattern.isNumber("123+"));
        assertFalse(defaultTokenPattern.isNumber("123 +"));
        assertFalse(defaultTokenPattern.isNumber("123qw"));
    }

    @Test
    void testIsSymbol() {
        DefaultTokenPattern defaultTokenPattern = new DefaultTokenPattern();
        assertTrue(defaultTokenPattern.isSymbol("+"));
        assertTrue(defaultTokenPattern.isSymbol("-"));
        assertTrue(defaultTokenPattern.isSymbol("*"));
        assertTrue(defaultTokenPattern.isSymbol("/"));
        assertTrue(defaultTokenPattern.isSymbol("("));
        assertTrue(defaultTokenPattern.isSymbol(")"));
        assertTrue(defaultTokenPattern.isSymbol("+ "));

        assertFalse(defaultTokenPattern.isSymbol(null));
        assertFalse(defaultTokenPattern.isSymbol(""));
        assertFalse(defaultTokenPattern.isSymbol("++"));
        assertFalse(defaultTokenPattern.isSymbol("kio"));
        assertFalse(defaultTokenPattern.isSymbol("9.8"));
    }

    @Test
    void testIsIdentifier() {
        DefaultTokenPattern defaultTokenPattern = new DefaultTokenPattern();
        assertTrue(defaultTokenPattern.isIdentifier("score_1"));

        assertFalse(defaultTokenPattern.isIdentifier(null));
        assertFalse(defaultTokenPattern.isIdentifier(""));
        assertFalse(defaultTokenPattern.isIdentifier("22.3"));
        assertFalse(defaultTokenPattern.isIdentifier("+"));
        assertFalse(defaultTokenPattern.isIdentifier("asd 1"));
        assertFalse(defaultTokenPattern.isIdentifier("asd_"));
        assertFalse(defaultTokenPattern.isIdentifier("asd+"));
    }
}