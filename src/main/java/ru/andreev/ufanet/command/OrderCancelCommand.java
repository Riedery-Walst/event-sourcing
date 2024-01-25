package ru.andreev.ufanet.command;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderCancelCommand {
    private UUID orderId;
    private int employeeId;
    private String cancelDescription;
}
