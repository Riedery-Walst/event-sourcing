package ru.andreev.ufanet.exception;

public class OrderNotFountException extends RuntimeException{
    public OrderNotFountException(String massage) {
        super(massage);
    }
}
