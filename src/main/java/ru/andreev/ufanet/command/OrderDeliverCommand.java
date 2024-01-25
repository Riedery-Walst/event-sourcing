package ru.andreev.ufanet.command;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderDeliverCommand {
    private UUID orderId;
    private int employeeId;
}
