package ru.andreev.ufanet.exception;

public class OrderIsNotRegisteredException extends RuntimeException{
    public OrderIsNotRegisteredException(String massage) {
        super(massage);
    }
}