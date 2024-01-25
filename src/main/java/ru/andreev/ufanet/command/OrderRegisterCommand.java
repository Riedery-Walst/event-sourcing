package ru.andreev.ufanet.command;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRegisterCommand {
    private int employeeId;
    private int customerId;
    private String deliveryTimeLeft;
    private int productId;
    private BigDecimal price;
}
