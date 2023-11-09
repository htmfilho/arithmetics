package com.sqbrains.arithmetic;

import java.util.List;

public class Expression {

    private List<Token> tokens;

    public Expression(String expression) {
        this.tokens = Token.tokenize(expression);

        // No tokens, no expression.
        if (this.tokens.isEmpty()) {
            throw new InvalidExpressionException();
        }

        // If there is only one token then it can only be a number.
        // Everything else is invalid.
        if (this.tokens.size() == 1 && !this.tokens.get(0).isNumber()) {
            throw new InvalidExpressionException();
        }

        // If there are only 2 tokens then it can only be a negative or positive number.
        // Everything else is invalid.
        if (this.tokens.size() == 2 && 
                (!this.tokens.get(0).getLexeme().equals(Token.MINUS) ||
                 !this.tokens.get(0).getLexeme().equals(Token.PLUS)) &&
                 !this.tokens.get(1).isNumber()) {
            throw new InvalidExpressionException();
        }
    }

    public List<Token> getTokens() {
        return this.tokens;
    }

    public Token compile() {
        return compile(0);
    }

    private Token compile(int currentPosition) {
        Token current = this.tokens.get(currentPosition);
        Token root = null;

        // In case the next token after the opening parenthesis is a closing parenthesis
        if (current.isClosingParentesis()) {
            throw new InvalidExpressionException();        
        }

        if (current.isOpeningParentesis() && currentPosition == 0) {
            root = compile(currentPosition + 1);
        }

        if (root == null) {
            root = current;
        }

        if (current.isNegative()) {
            current.addZeroChild();
        }        

        return root;
    }
}