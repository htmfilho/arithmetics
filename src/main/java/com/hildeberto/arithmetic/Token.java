package com.hildeberto.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Token<T> {
    private Token() {}

    private static Character[] symbols = {'*', '/', '+', '-', '(', ')'};
    private static final Pattern numberPattern = Pattern.compile("\\d+(\\.\\d*)?");
    private static final Pattern variablePattern = Pattern.compile("[a-zA-Z]+(_?[0-9a-zA-Z])*");

    private T value;

    public T getValue() {
        return value;
    }

    public static List<Token> tokenize(String expression) {
        if (expression == null) {
            return Collections.emptyList();
        }

        List<Token> tokens = new ArrayList<>();
        char[] cExpression = expression.toCharArray();
        StringBuilder tokenValue = new StringBuilder();

        Token<Float> numberToken = null;
        Token<Character> symbolToken = null;
        Token<String> variableToken = null;
        int pos = 0;

        while (pos < cExpression.length) {
            tokenValue.append(cExpression[pos]);

            if (Token.isSymbol(tokenValue.toString())) {
                if (symbolToken == null)
                    symbolToken = new Token<>();

                symbolToken.value = tokenValue.charAt(0);
                pos++;
                continue;
            } else if (symbolToken != null) {
                tokens.add(symbolToken);
                symbolToken = null;
                tokenValue = new StringBuilder();
            }

            if (Token.isNumber(tokenValue.toString())) {
                if (numberToken == null)
                    numberToken = new Token<>();

                numberToken.value = Float.valueOf(tokenValue.toString());
                pos++;
                continue;
            } else if (numberToken != null) {
                tokens.add(numberToken);
                numberToken = null;
                tokenValue = new StringBuilder();
            }

            if (Token.isVariable(tokenValue.toString())) {
                if (variableToken == null)
                    variableToken = new Token<>();

                variableToken.value = tokenValue.toString();
                pos++;
            } else if (variableToken != null) {
                tokens.add(variableToken);
                variableToken = null;
                tokenValue = new StringBuilder();
            }

            if (tokenValue.length() > cExpression.length)
                throw new UnrecognizedTokenException();
        }

        if (symbolToken != null) tokens.add(symbolToken);
        if (numberToken != null) tokens.add(numberToken);
        if (variableToken != null) tokens.add(variableToken);

        return tokens;
    }

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
