package com.example.multimedia2021.exceptions;

public class UndersizeException extends HangmanException{
    public UndersizeException(String error_message) {
        super("Too few words in dictionary, at least 20\nare required. Found only: " + error_message);
    }
}
