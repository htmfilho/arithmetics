package com.sqbrains.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Token<T> {
    private Token() {}

    private static Character[] symbols = {'*', '/', '+', '-', '(', ')'};
    private static final Pattern numberPattern = Pattern.compile("\\d+(\\.\\d*)?");
    private static final Pattern variablePattern = Pattern.compile("[a-zA-Z]+(_?[0-9a-zA-Z])*");

    private T lexeme;

    public T getLexeme() {
        return lexeme;
    }

    public static List<Token> tokenize(String expression) {
        if (expression == null) {
            return Collections.emptyList();
        }

        List<Token> tokens = new ArrayList<>();
        char[] cExpression = expression.toCharArray();
        StringBuilder lexeme = new StringBuilder();

        Token<Float> numberToken = null;
        Token<Character> symbolToken = null;
        Token<String> variableToken = null;
        int pos = 0;

        while (pos < cExpression.length) {
            lexeme.append(cExpression[pos]);

            if (Token.isSymbol(lexeme.toString())) {
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

            if (Token.isNumber(lexeme.toString())) {
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

            if (Token.isVariable(lexeme.toString())) {
                if (variableToken == null)
                    variableToken = new Token<>();

                variableToken.lexeme = lexeme.toString();
                pos++;
            } else if (variableToken != null) {
                tokens.add(variableToken);
                variableToken = null;
                lexeme = new StringBuilder();
            }

            if (lexeme.length() > cExpression.length)
                throw new UnrecognizedTokenException();
        }

        if (symbolToken != null) 
            tokens.add(symbolToken);
        
        if (numberToken != null) 
            tokens.add(numberToken);
        
        if (variableToken != null) 
            tokens.add(variableToken);

        return tokens;
    }

    /*public static Token createToken(String expression) {
        if (Token.isSymbol(expression)) return createSymbolToken(lexeme);

        return null;
    }

    public static Token<Character> createSymbolToken(char[] lexeme) {
        while (pos < lexeme.le)
    }*/

    public static boolean isNumber(String token) {
        if (token == null) {
            return false;
        }
        token = token.trim();
        return numberPattern.matcher(token).matches();
    }

    public static boolean isSymbol(String token) {
        if (token == null) {
            return false;
        }

        token = token.trim();

        if(token.length() != 1) {
            return false;
        }

        for (Character s: symbols) {
            if (s.equals(token.toCharArray()[0])) {
                return true;
            }
        }

        return false;
    }

    public static boolean isVariable(String token) {
        if (token == null) {
            return false;
        }
        token = token.trim();
        return variablePattern.matcher(token).matches();
    }
}
