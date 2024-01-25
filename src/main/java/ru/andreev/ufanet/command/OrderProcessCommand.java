package ru.andreev.ufanet.command;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderProcessCommand {
    private UUID orderId;
    private int employeeId;
}
