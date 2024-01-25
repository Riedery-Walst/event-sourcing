package ru.andreev.ufanet.command;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderReadyCommand {
    private UUID orderId;
    private int employeeId;
}
