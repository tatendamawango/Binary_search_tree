/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ktu.ds.lab2.gui;

/**
 Own situation used to check the parameters entered in the dialogue structures.
 */
public class ValidationException extends RuntimeException {

    // Situation code. According to it, the program response to the situation is programmed
    private int code;

    public ValidationException(String text) {
        // (-1) - it is agreed that this is a neutral code.
        this(text, -1);
    }

    public ValidationException(String message, int code) {
        super(message);
        if (code < -1) {
            throw new IllegalArgumentException("Illegal code in Validation Exception: " + code);
        }
        this.code = code;
    }

    public ValidationException(String message, Throwable throwable, int code) {
        super(message, throwable);
        if (code < -1) {
            throw new IllegalArgumentException("Illegal code in MyException: " + code);
        }
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
