package com.sqbrains.arithmetic;

import java.util.regex.Pattern;

public class DefaultTokenPattern implements TokenPattern {
    public static Character[] symbols = {MULTIPLY, DIVIDE, PLUS, MINUS, PARENTESIS_OPEN, PARENTESIS_CLOSE};
    
    private static final Pattern numberPattern = Pattern.compile("\\d+(\\.\\d*)?");
    private static final Pattern identifierPattern = Pattern.compile("[a-zA-Z]+(_?[0-9a-zA-Z])*");

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
}