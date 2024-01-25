package ru.andreev.ufanet.exception;

public class OrderIsDeliveredOrCanceledException extends RuntimeException {
    public OrderIsDeliveredOrCanceledException(String massage) {
        super(massage);
    }
}
