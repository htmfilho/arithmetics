package com.sqbrains.arithmetic;

public interface TokenPattern {
    public static final Character PARENTESIS_OPEN = '(';
    public static final Character PARENTESIS_CLOSE = ')';
    public static final Character DIVIDE = '/';
    public static final Character MULTIPLY = '*';
    public static final Character MINUS = '-';
    public static final Character PLUS = '+';
    
    boolean isSymbol(String lexeme);
    boolean isNumber(String lexeme);
    boolean isIdentifier(String lexeme);
}