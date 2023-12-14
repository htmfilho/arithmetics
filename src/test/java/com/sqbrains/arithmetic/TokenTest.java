package com.sqbrains.arithmetic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {
    @Test
    void testTokenize() {
        List<Token> tokens = Token.tokenize("2 * 8 + 3 / 4 - 5");
        assertEquals(9, tokens.size());
        assertEquals(2.0f, tokens.get(0).getLexeme());
        assertEquals('*', tokens.get(1).getLexeme());
        assertEquals(8f, tokens.get(2).getLexeme());
        assertEquals('+', tokens.get(3).getLexeme());
        assertEquals(3f, tokens.get(4).getLexeme());
        assertEquals('/', tokens.get(5).getLexeme());
        assertEquals(4f, tokens.get(6).getLexeme());
        assertEquals('-', tokens.get(7).getLexeme());
        assertEquals(5f, tokens.get(8).getLexeme());

        tokens = Token.tokenize("6/2*(2+1)");
        assertEquals(9, tokens.size());

        tokens = Token.tokenize("6  /  (2*(2 + 1 ))");
        assertEquals(11, tokens.size());

        tokens = Token.tokenize("6  /  (tax*(2 + debt))");
        assertEquals(11, tokens.size());
        assertEquals("tax", tokens.get(3).getLexeme());
        assertEquals("debt", tokens.get(8).getLexeme());

        tokens = Token.tokenize("(price * tax) + price");
        assertEquals("price", tokens.get(6).getLexeme());

        assertThrows(UnrecognizedTokenException.class, () -> Token.tokenize("6  /  (2*(2 + 1 )) %"));

        tokens = Token.tokenize("6 / 2 * (-2 + 1)");
        assertEquals(10, tokens.size());

        tokens = Token.tokenize("");
        assertEquals(0, tokens.size());

        tokens = Token.tokenize(null);
        assertEquals(0, tokens.size());
    }

    @Test
    void testIsNumber() {
        List<Token> tokens = Token.tokenize("6");
        assertTrue(tokens.get(0).isNumber());
    }

    @Test
    void testIsIdentifier() {
        List<Token> tokens = Token.tokenize("ty");
        assertTrue(tokens.get(0).isIdentifier());
    }

    @Test
    void testIsOperator() {
        List<Token> tokens = Token.tokenize("* + / - q");
        
        assertTrue(tokens.get(0).isOperator());
        assertTrue(tokens.get(1).isOperator());
        assertTrue(tokens.get(2).isOperator());
        assertTrue(tokens.get(3).isOperator());
        assertFalse(tokens.get(4).isOperator());
    }

    @Test
    void testSymbols() {
        List<Token> tokens = Token.tokenize("()-");

        assertTrue(tokens.get(0).isOpeningParentesis());
        assertTrue(tokens.get(1).isClosingParentesis());
        assertTrue(tokens.get(2).isNegative());
    }

    @Test
    void testChildren() {
        List<Token> tokens = Token.tokenize("-3");
        tokens.get(0).addZeroChild();
        tokens.get(0).addChild(tokens.get(1));
        assertEquals(0.0f, tokens.get(0).getLeftChild().getLexeme());
        assertEquals(3f, tokens.get(0).getRightChild().getLexeme());

        tokens = Token.tokenize("3+4");
        tokens.get(1).addChild(tokens.get(0));
        tokens.get(1).addChild(tokens.get(2));
        assertEquals(3f, tokens.get(1).getLeftChild().getLexeme());
        assertEquals(4f, tokens.get(1).getRightChild().getLexeme());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/expressions-short.csv", numLinesToSkip = 1)
    void testExpressionsFromTheField(String expression, int size) {
        List<Token> tokens = Token.tokenize(expression, new TokenPattern() {
            private Character[] symbols = {MULTIPLY, DIVIDE, PLUS, MINUS, PARENTESIS_OPEN, PARENTESIS_CLOSE};
    
            private final Pattern numberPattern = Pattern.compile("\\d+(\\.\\d*)?");
            private final Pattern identifierPattern = Pattern.compile("\\[[a-zA-Z]+(_?[0-9a-zA-Z])*\\]");

            public boolean isSymbol(String lexeme) {
                if (lexeme == null) {
                    return false;
                }

                lexeme = lexeme.trim();

                if(lexeme.length() != 1) {
                    return false;
                }

                for (Character s: symbols) {
                    if (s.equals(lexeme.toCharArray()[0])) {
                        return true;
                    }
                }

                return false;
            }

            public boolean isNumber(String lexeme) {
                if (lexeme == null) {
                    return false;
                }

                lexeme = lexeme.trim();
                return numberPattern.matcher(lexeme).matches();
            }

            public boolean isIdentifier(String lexeme) {
                if (lexeme == null) {
                    return false;
                }
                lexeme = lexeme.trim();
                return identifierPattern.matcher(lexeme).matches();
            }
        });
        assertEquals(size, tokens.size());
    }
}