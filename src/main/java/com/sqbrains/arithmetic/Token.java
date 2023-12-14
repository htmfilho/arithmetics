package com.sqbrains.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Token<T> {
    private T lexeme;
    private Token[] children;

    private Token() {
        this.children = new Token[2];
    }

    private Token(T lexeme) {
        this.lexeme = lexeme;
    }

    public T getLexeme() {
        return lexeme;
    }

    public void addChild(Token token) {
        if (this.children[0] == null) {
            this.children[0] = token;
        } else {
            this.children[1] = token;
        }
    }

    public void addZeroChild() {
        this.children[0] = new Token<Float>(0f);
    }

    public Token getLeftChild() {
        return this.children[0];
    }

    public Token getRightChild() {
        return this.children[1];
    }

    public boolean isNumber() {
        return this.lexeme instanceof Float;
    }

    public boolean isIdentifier() {
        return this.lexeme instanceof String;
    }

    public boolean isOperator() {
        if (this.lexeme instanceof Character) {
            return lexeme.equals(TokenPattern.MULTIPLY) || lexeme.equals(TokenPattern.DIVIDE) || lexeme.equals(TokenPattern.PLUS) || lexeme.equals(TokenPattern.MINUS);
        }
        
        return false;
    }

    public boolean isOpeningParentesis() {
        return this.lexeme.equals(TokenPattern.PARENTESIS_OPEN);
    }

    public boolean isClosingParentesis() {
        return this.lexeme.equals(TokenPattern.PARENTESIS_CLOSE);
    }

    public boolean isNegative() {
        return this.lexeme.equals(TokenPattern.MINUS);
    }

    public static List<Token> tokenize(String expression) {
        return tokenize(expression, new DefaultTokenPattern());
    }
 
    public static List<Token> tokenize(String expression, TokenPattern tokenPattern) {
        if (expression == null) {
            return Collections.emptyList();
        }

        List<Token> tokens = new ArrayList<>();
        char[] cExpression = expression.toCharArray();
        StringBuilder lexeme = new StringBuilder();

        Token<Float> numberToken = null;
        Token<Character> symbolToken = null;
        Token<String> identifierToken = null;
        int pos = 0;

        while (pos < cExpression.length) {
            lexeme.append(cExpression[pos]);

            if (tokenPattern.isSymbol(lexeme.toString())) {
                if (symbolToken == null)
                    symbolToken = new Token<>();

                symbolToken.lexeme = lexeme.charAt(0);
                pos++;
                continue;
            } else if (symbolToken != null) {
                tokens.add(symbolToken);
                symbolToken = null;
                lexeme = new StringBuilder();
            }

            if (tokenPattern.isNumber(lexeme.toString())) {
                if (numberToken == null)
                    numberToken = new Token<>();

                numberToken.lexeme = Float.valueOf(lexeme.toString());
                pos++;
                continue;
            } else if (numberToken != null) {
                tokens.add(numberToken);
                numberToken = null;
                lexeme = new StringBuilder();
            }

            if (tokenPattern.isIdentifier(lexeme.toString())) {
                if (identifierToken == null)
                    identifierToken = new Token<>();

                identifierToken.lexeme = lexeme.toString();
                pos++;
            } else if (identifierToken != null) {
                tokens.add(identifierToken);
                identifierToken = null;
                lexeme = new StringBuilder();
            }

            if (lexeme.length() > cExpression.length)
                throw new UnrecognizedTokenException(lexeme.toString());
        }

        if (symbolToken != null) 
            tokens.add(symbolToken);
        
        if (numberToken != null) 
            tokens.add(numberToken);
        
        if (identifierToken != null) 
            tokens.add(identifierToken);

        return tokens;
    }
}
