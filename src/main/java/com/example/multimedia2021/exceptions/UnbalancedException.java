package com.example.multimedia2021.exceptions;

public class UnbalancedException extends HangmanException{
    public UnbalancedException(String error_message) {
        super("Expected over 20% of words to have nine\nletters or more. The percentage was: " + error_message);
    }
}
